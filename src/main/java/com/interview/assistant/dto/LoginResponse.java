package com.interview.assistant.dto;

import com.interview.assistant.enums.Role;

public class LoginResponse {
    private String username;
    private Role role;
    private String uniqueId;
    private String message;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    public String getUniqueId() { return uniqueId; }
    public void setUniqueId(String uniqueId) { this.uniqueId = uniqueId; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
