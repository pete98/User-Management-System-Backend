package com.pranav_sailor.user_management_system.service;

import com.pranav_sailor.user_management_system.dto.UpdateUserRoleDTO;
import com.pranav_sailor.user_management_system.dto.UserDTO;
import com.pranav_sailor.user_management_system.entity.User;
import com.pranav_sailor.user_management_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    //Retrieve all users
    public List<UserDTO> getAllUsers(){
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    //Update user roles
    public UserDTO updateUserRole(Long id, UpdateUserRoleDTO updateUserRoleDTO){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found "+id));
        user.setRoles(updateUserRoleDTO.getRoles());
        User updatedUser = userRepository.save(user);
        return mapToDTO(updatedUser);
    }


    //Helper method to map User to UserDTO
    private UserDTO mapToDTO(User user){
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .roles(user.getRoles())
                .build();
    }


}
