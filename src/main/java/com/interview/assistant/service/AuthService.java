package com.interview.assistant.service;

import com.interview.assistant.dto.LoginRequest;
import com.interview.assistant.dto.LoginResponse;
import com.interview.assistant.entity.User;
import com.interview.assistant.enums.Role;
import com.interview.assistant.enums.UserStatus;
import com.interview.assistant.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final SessionService sessionService;

    public AuthService(UserRepository userRepository, SessionService sessionService) {
        this.userRepository = userRepository;
        this.sessionService = sessionService;
    }

    public LoginResponse login(LoginRequest request, HttpSession session) {
        User user = userRepository.findByUsername(request.getUsername())
                .filter(existing -> existing.getPassword().equals(request.getPassword()))
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        // Hinglish: receiver ke liye uniqueId auto-generate ya refresh kar sakte hain.
        if (user.getRole() == Role.RECEIVER && (user.getUniqueId() == null || user.getUniqueId().isBlank())) {
            user.setUniqueId(UUID.randomUUID().toString().substring(0, 8));
        }
        user.setStatus(UserStatus.ONLINE);
        userRepository.save(user);

        session.setAttribute("username", user.getUsername());
        session.setAttribute("role", user.getRole().name());

        if (user.getRole() == Role.SENDER) {
            sessionService.markSenderOnline(user.getUsername());
        }

        LoginResponse response = new LoginResponse();
        response.setUsername(user.getUsername());
        response.setRole(user.getRole());
        response.setUniqueId(user.getUniqueId());
        response.setMessage("Login successful");
        return response;
    }

    public void logout(String username) {
        userRepository.findByUsername(username).ifPresent(user -> {
            if (user.getRole() == Role.RECEIVER && user.getUniqueId() != null) {
                sessionService.markReceiverOffline(user.getUniqueId());
            } else if (user.getRole() == Role.SENDER) {
                sessionService.markSenderOffline(user.getUsername());
            }
            user.setStatus(UserStatus.OFFLINE);
            userRepository.save(user);
        });
    }
}
