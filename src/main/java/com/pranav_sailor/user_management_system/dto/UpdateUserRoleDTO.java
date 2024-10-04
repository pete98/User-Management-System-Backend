package com.pranav_sailor.user_management_system.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

//Purpose: Facilitates updating a user's roles, allowing admin users to assign or revoke roles.

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserRoleDTO {
    private Set<String> roles;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @Size(max = 20, message = "First Name can have at most 20 characters")
    private String firstName;

    @Size(max = 20, message = "First Name can have at most 20 characters")
    private String lastName;
}
