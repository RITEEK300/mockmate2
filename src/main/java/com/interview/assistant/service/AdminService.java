package com.interview.assistant.service;

import com.interview.assistant.entity.Question;
import com.interview.assistant.entity.User;
import com.interview.assistant.enums.Role;
import com.interview.assistant.repository.QuestionRepository;
import com.interview.assistant.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminService {
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final SessionService sessionService;

    public AdminService(UserRepository userRepository, QuestionRepository questionRepository, SessionService sessionService) {
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
        this.sessionService = sessionService;
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public List<User> allReceivers() {
        return userRepository.findByRole(Role.RECEIVER);
    }

    public Map<String, Object> activeSessionStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("activeReceivers", sessionService.getReceiverSessionMap());
        stats.put("activeSenders", sessionService.getActiveSenders());
        return stats;
    }

    public List<Question> allQuestions() {
        return questionRepository.findAll();
    }

    public Question createQuestion(Question question) {
        return questionRepository.save(question);
    }

    public Question updateQuestion(Long id, Question payload) {
        Question existing = questionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Question not found"));
        existing.setKeyword(payload.getKeyword());
        existing.setQuestion(payload.getQuestion());
        existing.setAnswer(payload.getAnswer());
        existing.setCategory(payload.getCategory());
        return questionRepository.save(existing);
    }

    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }
}
