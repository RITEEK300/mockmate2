package com.interview.assistant.controller;

import com.interview.assistant.dto.AnswerMessage;
import com.interview.assistant.dto.SendAnswerRequest;
import com.interview.assistant.service.AnswerService;
import jakarta.validation.Valid;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
public class InterviewSocketController {

    private final AnswerService answerService;

    public InterviewSocketController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @MessageMapping("/send")
    public void send(@Valid @Payload SendAnswerRequest payload) {
        // Hinglish: websocket route se sender message bhejta hai, backend process karke receiver ko push karta hai.
        AnswerMessage ignored = answerService.processAndSend("SENDER_SOCKET", payload.getUniqueId(), payload.getQuestion());
    }
}
