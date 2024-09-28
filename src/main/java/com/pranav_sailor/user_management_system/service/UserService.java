package com.pranav_sailor.user_management_system.service;

import com.pranav_sailor.user_management_system.dto.RegisterRequest;
import com.pranav_sailor.user_management_system.dto.UserDTO;
import com.pranav_sailor.user_management_system.entity.User;
import com.pranav_sailor.user_management_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //Register a new User
    public UserDTO registerUser(RegisterRequest registerRequest){
        //Check if username or email already exists
        if (userRepository.existsByUsername(registerRequest.getUsername())){
            throw  new IllegalArgumentException("Username is already taken!");
        }

        if(userRepository.existsByEmail(registerRequest.getEmail())){
            throw new IllegalArgumentException("Email is already in use!");
        }

        //Create new user with default role
        User user = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .roles(Set.of("ROLE_USER")) //Default Role
                .build();

        User savedUser = userRepository.save(user);

        return mapToDTO(savedUser);

    }

    //Fetch user bu username
    public UserDTO getUserByUsername(String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return mapToDTO(user);
    }

    //Fetch user by ID
    public UserDTO getUserById(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"User Id not found"));
        return mapToDTO(user);
    }


    //Helper method to map User to UserDTO
    private UserDTO mapToDTO(User user){
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();

    }


}