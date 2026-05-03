package com.interview.assistant.controller;

import com.interview.assistant.dto.AnswerMessage;
import com.interview.assistant.dto.SendAnswerRequest;
import com.interview.assistant.service.AnswerService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sender")
public class SenderController {

    private final AnswerService answerService;

    public SenderController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping("/generate")
    public AnswerMessage generate(@Valid @RequestBody SendAnswerRequest request) {
        return answerService.processAndSend("SENDER_API", request.getUniqueId(), request.getQuestion());
    }

    @PostMapping("/preview")
    public AnswerMessage preview(@Valid @RequestBody SendAnswerRequest request) {
        return answerService.previewAnswer(request.getQuestion());
    }
}
