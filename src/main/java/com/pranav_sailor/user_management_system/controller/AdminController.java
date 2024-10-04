package com.pranav_sailor.user_management_system.controller;

import com.pranav_sailor.user_management_system.dto.UpdateUserRoleDTO;
import com.pranav_sailor.user_management_system.dto.UserDTO;
import com.pranav_sailor.user_management_system.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    //Endpoint to get all users
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        List<UserDTO> users = adminService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    //Endpoint to update user roles
    @PutMapping("/users/{id}/roles")
    public ResponseEntity<UserDTO> updateUserRoles(@PathVariable Long id, @Valid @RequestBody UpdateUserRoleDTO updateUserRoleDTO){
        UserDTO updatedUser = adminService.updateUserRole(id, updateUserRoleDTO);
        return ResponseEntity.ok(updatedUser);
    }


}
