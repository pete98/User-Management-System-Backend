package com.pranav_sailor.user_management_system.controller;

import com.pranav_sailor.user_management_system.dto.LoginRequest;
import com.pranav_sailor.user_management_system.dto.RegisterRequest;
import com.pranav_sailor.user_management_system.dto.UserDTO;
import com.pranav_sailor.user_management_system.security.JwtTokenProvider;
import com.pranav_sailor.user_management_system.service.UserService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider tokenProvider;

    /**
     * User registration endpoint.
     *
     * @param registerRequest the registration request containing user details
     * @return the registered user details
     */
    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        UserDTO userDTO = userService.registerUser(registerRequest);
        return ResponseEntity.ok(userDTO);
    }

    /**
     * User login endpoint.
     *
     * @param loginRequest the login request containing username and password
     * @return JWT authentication response containing the token
     */
    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    /**
     * Inner class representing the JWT authentication response.
     */
    @Getter
    public static class JwtAuthenticationResponse {
        private final String accessToken;
        private final String tokenType = "Bearer";

        public JwtAuthenticationResponse(String accessToken) {
            this.accessToken = accessToken;
        }
    }
}
