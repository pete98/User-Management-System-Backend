package com.pranav_sailor.user_management_system.service;

import com.pranav_sailor.user_management_system.dto.ChangePasswordDTO;
import com.pranav_sailor.user_management_system.dto.RegisterRequest;
import com.pranav_sailor.user_management_system.dto.UpdateUserProfileDTO;
import com.pranav_sailor.user_management_system.dto.UserDTO;
import com.pranav_sailor.user_management_system.entity.User;
import com.pranav_sailor.user_management_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    //Update user profile
    public UserDTO updateUserProfile(String username, UpdateUserProfileDTO updateUserProfileDTO){
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("Username not found" + username));

        user.setEmail(updateUserProfileDTO.getEmail());
        user.setFirstName(updateUserProfileDTO.getFirstName());
        user.setLastName(updateUserProfileDTO.getLastName());

        User updatedUser = userRepository.save(user);
        return mapToDTO(updatedUser);
    }

    //Change user password
    public UserDTO changeUserPassword(String username, ChangePasswordDTO changePasswordDTO){
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("Username not found" + username));

        //Verify current password
        if(!passwordEncoder.matches(changePasswordDTO.getCurrentPassword(), user.getPassword())){
            throw new IllegalArgumentException("Current password is incorrect");
        }

        //Update to new password
        user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
        userRepository.save(user);

        return mapToDTO(user);
    }

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
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"User id not found"));
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