package com.interview.assistant.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendAnswerRequest {
    @NotBlank(message = "Receiver unique ID is required")
    @Size(min = 5, max = 100, message = "Unique ID must be between 5 and 100 characters")
    private String uniqueId;
    
    @NotBlank(message = "Question is required")
    @Size(min = 10, max = 1000, message = "Question must be between 10 and 1000 characters")
    private String question;
}
