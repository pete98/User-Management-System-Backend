package com.pranav_sailor.user_management_system.controller;

import com.pranav_sailor.user_management_system.dto.ChangePasswordDTO;
import com.pranav_sailor.user_management_system.dto.UpdateUserProfileDTO;
import com.pranav_sailor.user_management_system.dto.UserDTO;
import com.pranav_sailor.user_management_system.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Get current user's details
    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        UserDTO userDTO = userService.getUserByUsername(userDetails.getUsername());
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping("/me")
    public ResponseEntity<UserDTO> updateUserProfile(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody UpdateUserProfileDTO updateUserProfileDTO){
        UserDTO updatedUser = userService.updateUserProfile(userDetails.getUsername(), updateUserProfileDTO);
        return ResponseEntity.ok(updatedUser);
    }

    //Change user password
    @PutMapping("/me/password")
    public ResponseEntity<Void> changeUserPassword(@AuthenticationPrincipal UserDetails userDetails,
                                                   @Valid @RequestBody ChangePasswordDTO changePasswordDTO){
        userService.changeUserPassword(userDetails.getUsername(), changePasswordDTO);
        return ResponseEntity.noContent().build();
    }
}