package com.interview.assistant.config;

import com.interview.assistant.entity.User;
import com.interview.assistant.enums.Role;
import com.interview.assistant.enums.UserStatus;
import com.interview.assistant.repository.QuestionRepository;
import com.interview.assistant.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeedDataConfig {

    @Bean
    CommandLineRunner seedData(UserRepository userRepository, QuestionRepository questionRepository) {
        return args -> {
            if (userRepository.count() == 0) {
                // Hinglish: demo users seed kar rahe hain, production me secure password hashing lagega.
                userRepository.save(newUser("admin", "admin123", Role.ADMIN, null));
                userRepository.save(newUser("sender1", "sender123", Role.SENDER, null));
                userRepository.save(newUser("receiver1", "receiver123", Role.RECEIVER, "rcv1001"));
            }
        };
    }

    private User newUser(String username, String password, Role role, String uniqueId) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        user.setUniqueId(uniqueId);
        user.setStatus(UserStatus.OFFLINE);
        return user;
    }

}
