package com.interview.assistant.controller;

import com.interview.assistant.dto.AnswerMessage;
import com.interview.assistant.service.HistoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/receiver")
public class ReceiverController {

    private final HistoryService historyService;

    public ReceiverController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping("/history/{uniqueId}")
    public List<AnswerMessage> history(@PathVariable String uniqueId) {
        return historyService.getRecent(uniqueId);
    }
}
