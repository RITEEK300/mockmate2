package com.interview.assistant.controller;

import com.interview.assistant.dto.LoginRequest;
import com.interview.assistant.dto.LoginResponse;
import com.interview.assistant.service.AuthService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request, HttpSession session) {
        return authService.login(request, session);
    }

    @PostMapping("/logout")
    public Map<String, String> logout(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username != null) {
            authService.logout(username);
        }
        session.invalidate();
        return Map.of("message", "Logged out");
    }
}
