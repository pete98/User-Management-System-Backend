package com.pranav_sailor.user_management_system.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

// Purpose: Captures login credentials with validation.

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;
}
