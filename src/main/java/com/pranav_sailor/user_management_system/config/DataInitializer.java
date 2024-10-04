package com.pranav_sailor.user_management_system.config;

import com.pranav_sailor.user_management_system.entity.User;
import com.pranav_sailor.user_management_system.repository.UserRepository;
import com.pranav_sailor.user_management_system.security.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void run(String... args) throws Exception {
        //Check if admin user exists
        if(!userRepository.existsByUsername("admin")){
            //Create Admin User
            User admin = User.builder()
                    .username("admin")
                    .email("admin@example.com")
                    .password(passwordEncoder.encode("adminPassword123"))
                    .firstName("Pranav")
                    .lastName("Sailor")
                    .roles(Set.of(Role.ROLE_USER, Role.ROLE_ADMIN))
                    .build();
            userRepository.save(admin);
            System.out.println("Admin user created with username: admin and password: adminPassword123");

        } else {
            System.out.println("Admin user already exists");
        }
    }
}
