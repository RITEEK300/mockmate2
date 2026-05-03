package com.interview.assistant.repository;

import com.interview.assistant.entity.User;
import com.interview.assistant.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByUniqueId(String uniqueId);
    List<User> findByRole(Role role);
}
