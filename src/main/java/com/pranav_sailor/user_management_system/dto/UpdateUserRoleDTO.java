package com.pranav_sailor.user_management_system.dto;

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
}
