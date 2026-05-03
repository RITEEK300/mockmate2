package com.interview.assistant.controller;

import com.interview.assistant.dto.AnswerMessage;
import com.interview.assistant.entity.Question;
import com.interview.assistant.entity.SystemLog;
import com.interview.assistant.entity.User;
import com.interview.assistant.service.AdminService;
import com.interview.assistant.service.AnswerService;
import com.interview.assistant.service.LogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;
    private final LogService logService;
    private final AnswerService answerService;

    public AdminController(AdminService adminService, LogService logService, AnswerService answerService) {
        this.adminService = adminService;
        this.logService = logService;
        this.answerService = answerService;
    }

    @GetMapping("/users")
    public List<User> users() { return adminService.allUsers(); }

    @GetMapping("/receivers")
    public List<User> receivers() { return adminService.allReceivers(); }

    @GetMapping("/sessions")
    public Map<String, Object> sessions() { return adminService.activeSessionStats(); }

    @GetMapping("/logs")
    public List<SystemLog> logs() { return logService.getRecentLogs(); }

    @GetMapping("/questions")
    public List<Question> questions() { return adminService.allQuestions(); }

    @PostMapping("/questions")
    public Question createQuestion(@RequestBody Question question) { return adminService.createQuestion(question); }

    @PutMapping("/questions/{id}")
    public Question updateQuestion(@PathVariable Long id, @RequestBody Question payload) {
        return adminService.updateQuestion(id, payload);
    }

    @DeleteMapping("/questions/{id}")
    public void deleteQuestion(@PathVariable Long id) { adminService.deleteQuestion(id); }

    @PostMapping("/override")
    public AnswerMessage override(@RequestBody Map<String, String> payload) {
        // Hinglish: admin direct receiver ko answer push kar sakta hai.
        return answerService.processAndSend("ADMIN_OVERRIDE", payload.get("uniqueId"), payload.get("question"));
    }
}
