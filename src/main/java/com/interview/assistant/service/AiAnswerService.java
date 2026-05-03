package com.interview.assistant.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@Service
public class AiAnswerService {
    private final RestTemplate restTemplate;

    @Value("${app.ai.enabled:false}")
    private boolean aiEnabled;

    @Value("${app.ai.api-url:https://api.openai.com/v1/chat/completions}")
    private String apiUrl;

    @Value("${app.ai.model:gpt-4o-mini}")
    private String model;

    @Value("${app.ai.provider:gemini}")
    private String provider;

    @Value("${app.ai.api-key:}")
    private String apiKey;

    public AiAnswerService(RestTemplateBuilder builder) {
        this.restTemplate = builder
                .setConnectTimeout(Duration.ofSeconds(5))
                .setReadTimeout(Duration.ofSeconds(20))
                .build();
    }

    public String generateAnswer(String question) {
        if (!aiEnabled || apiKey == null || apiKey.isBlank()) {
            return fallbackAnswer(question);
        }

        try {
            if ("gemini".equalsIgnoreCase(provider)) {
                return callGemini(question);
            }
            return callOpenAi(question);
        } catch (Exception ignored) {
            return fallbackAnswer(question);
        }
    }

    private String callOpenAi(String question) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> payload = Map.of(
                "model", model,
                "temperature", 0.5,
                "messages", List.of(
                        Map.of(
                                "role", "system",
                                "content", "You are an interview assistant. Provide concise, professional, practical answers in 4-6 bullet points."
                        ),
                        Map.of(
                                "role", "user",
                                "content", question
                        )
                )
        );

        ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl, new HttpEntity<>(payload, headers), Map.class);
        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            return fallbackAnswer(question);
        }

        Object choicesObj = response.getBody().get("choices");
        if (!(choicesObj instanceof List<?> choices) || choices.isEmpty()) {
            return fallbackAnswer(question);
        }
        Object first = choices.get(0);
        if (!(first instanceof Map<?, ?> firstMap)) {
            return fallbackAnswer(question);
        }
        Object messageObj = firstMap.get("message");
        if (!(messageObj instanceof Map<?, ?> messageMap)) {
            return fallbackAnswer(question);
        }
        Object contentObj = messageMap.get("content");
        if (!(contentObj instanceof String content) || content.isBlank()) {
            return fallbackAnswer(question);
        }
        return content.trim();
    }

    private String callGemini(String question) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-goog-api-key", apiKey);

        Map<String, Object> payload = Map.of(
                "contents", List.of(
                        Map.of(
                                "parts", List.of(
                                        Map.of("text", "You are an interview assistant. Answer in a crisp, professional style.\n\nQuestion: " + question)
                                )
                        )
                ),
                "generationConfig", Map.of(
                        "temperature", 0.5,
                        "maxOutputTokens", 350
                )
        );

        String finalUrl = apiUrl;
        if (finalUrl == null || finalUrl.isBlank() || finalUrl.contains("openai.com")) {
            finalUrl = "https://generativelanguage.googleapis.com/v1beta/models/" + model + ":generateContent";
        }

        ResponseEntity<Map> response = restTemplate.postForEntity(finalUrl, new HttpEntity<>(payload, headers), Map.class);
        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            return fallbackAnswer(question);
        }

        Object candidatesObj = response.getBody().get("candidates");
        if (!(candidatesObj instanceof List<?> candidates) || candidates.isEmpty()) {
            return fallbackAnswer(question);
        }
        Object first = candidates.get(0);
        if (!(first instanceof Map<?, ?> firstMap)) {
            return fallbackAnswer(question);
        }
        Object contentObj = firstMap.get("content");
        if (!(contentObj instanceof Map<?, ?> contentMap)) {
            return fallbackAnswer(question);
        }
        Object partsObj = contentMap.get("parts");
        if (!(partsObj instanceof List<?> parts) || parts.isEmpty()) {
            return fallbackAnswer(question);
        }
        Object part = parts.get(0);
        if (!(part instanceof Map<?, ?> partMap)) {
            return fallbackAnswer(question);
        }
        Object textObj = partMap.get("text");
        if (!(textObj instanceof String text) || text.isBlank()) {
            return fallbackAnswer(question);
        }
        return text.trim();
    }

    private String fallbackAnswer(String question) {
        return "Suggested interview answer:\n"
                + "- Start with a clear one-line response to the question.\n"
                + "- Explain your approach with a practical example.\n"
                + "- Highlight measurable impact and ownership.\n"
                + "- Mention trade-offs and what you learned.\n"
                + "- Close with how this applies to the role.\n\n"
                + "Context prompt: " + question;
    }
}
