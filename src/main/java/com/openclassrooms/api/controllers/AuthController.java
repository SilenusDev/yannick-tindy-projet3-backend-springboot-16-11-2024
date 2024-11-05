package com.openclassrooms.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.openclassrooms.api.dto.LoginRequest;
import com.openclassrooms.api.dto.LoginResponse;
import com.openclassrooms.api.dto.UserDTO;
import com.openclassrooms.api.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            String token = userService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
            return ResponseEntity.ok(new LoginResponse(token));
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    // @GetMapping("/me")
    // public ResponseEntity<UserDTO> getCurrentUser(Authentication authentication) {
    //     UserDTO user = userService.getCurrentUser(authentication.getName());
    //     return ResponseEntity.ok(user);
    // }
}