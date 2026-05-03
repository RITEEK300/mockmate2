package com.interview.assistant.service;

import com.interview.assistant.dto.AnswerMessage;
import com.interview.assistant.entity.Question;
import com.interview.assistant.repository.QuestionRepository;
import com.interview.assistant.repository.UserRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AnswerService {

    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final SessionService sessionService;
    private final HistoryService historyService;
    private final LogService logService;
    private final SimpMessagingTemplate messagingTemplate;
    private final AiAnswerService aiAnswerService;

    public AnswerService(
            QuestionRepository questionRepository,
            UserRepository userRepository,
            SessionService sessionService,
            HistoryService historyService,
            LogService logService,
            SimpMessagingTemplate messagingTemplate,
            AiAnswerService aiAnswerService
    ) {
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.sessionService = sessionService;
        this.historyService = historyService;
        this.logService = logService;
        this.messagingTemplate = messagingTemplate;
        this.aiAnswerService = aiAnswerService;
    }

    public AnswerMessage processAndSend(String senderName, String uniqueId, String question) {
        userRepository.findByUniqueId(uniqueId)
                .orElseThrow(() -> new IllegalArgumentException("Receiver uniqueId not found"));

        if (!sessionService.isReceiverOnline(uniqueId)) {
            throw new IllegalStateException("Receiver is offline");
        }

        AnswerMessage message = previewAnswer(question);
        message.setUniqueId(uniqueId);

        historyService.save(uniqueId, question, message.getAnswer(), message.getSource());
        logService.log(senderName, uniqueId, "Sent answer via " + message.getSource() + " for question: " + question);

        // Hinglish: /user/queue/answers pe direct targeted message push karte hain.
        messagingTemplate.convertAndSendToUser(uniqueId, "/queue/answers", message);
        return message;
    }

    public AnswerMessage previewAnswer(String question) {
        String cleanQuestion = question == null ? "" : question.trim();
        Question best = findBestDbMatch(cleanQuestion);
        String answer;
        String source;
        if (best != null) {
            answer = best.getAnswer();
            source = "DB";
        } else {
            answer = aiAnswerService.generateAnswer(cleanQuestion);
            source = "AI_FALLBACK";
        }

        AnswerMessage message = new AnswerMessage();
        message.setQuestion(cleanQuestion);
        message.setAnswer(answer);
        message.setSource(source);
        message.setTimestamp(LocalDateTime.now());
        return message;
    }

    private Question findBestDbMatch(String question) {
        Set<String> tokens = tokenize(question);
        if (tokens.isEmpty()) {
            return null;
        }

        return questionRepository.findAll().stream()
                .filter(q -> q.getKeyword() != null)
                .max(Comparator.comparingInt(q -> score(q, tokens)))
                .filter(q -> score(q, tokens) >= 4)
                .orElse(null);
    }

    private int score(Question question, Set<String> tokens) {
        String keywordText = normalize(question.getKeyword());
        String questionText = normalize(question.getQuestion());
        int hits = 0;
        for (String token : tokens) {
            if (keywordText.contains(token)) {
                hits += 2;
            }
            if (questionText.contains(token)) {
                hits += 1;
            }
        }
        return hits;
    }

    private Set<String> tokenize(String text) {
        return Arrays.stream(normalize(text).split("\\s+"))
                .map(String::trim)
                .filter(s -> s.length() > 1)
                .collect(Collectors.toSet());
    }

    private String normalize(String text) {
        if (text == null) {
            return "";
        }
        return text.toLowerCase(Locale.ENGLISH).replaceAll("[^a-z0-9\\s,]", " ");
    }
}
