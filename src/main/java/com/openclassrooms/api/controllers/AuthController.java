package com.openclassrooms.api.controllers;

import org.hibernate.engine.internal.Collections;
import org.hibernate.mapping.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.openclassrooms.api.dto.LoginRequest;
import com.openclassrooms.api.dto.LoginResponse;
import com.openclassrooms.api.dto.RegisterRequest;
import com.openclassrooms.api.dto.UserDTO;
import com.openclassrooms.api.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        try {
            // Vérification si l'email existe déjà
            if (userService.emailExists(request.getEmail())) {
                return ResponseEntity.badRequest()
                    .body("Cet email est déjà utilisé");
            }
            
            // Tentative d'enregistrement
            String token = userService.register(
                request.getName(),
                request.getEmail(),
                request.getPassword(),
                request.getRole() 
            );
            
            // Si succès, retourner le token
            return ResponseEntity.ok(new LoginResponse(token));
            
        } catch (Exception e) {
            // Log l'erreur pour le débogage
            System.out.println("Erreur lors de l'enregistrement: " + e.getMessage());
            
            // Retourner un message d'erreur plus spécifique
            return ResponseEntity.badRequest()
                .body("Erreur lors de l'enregistrement: " + e.getMessage());
        }
    }

    // @PostMapping("/register")
    // public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
    //     try {
    //         String token = userService.register(
    //             request.getName(),
    //             request.getEmail(),
    //             request.getPassword()
    //         );
    //         return ResponseEntity.ok(new LoginResponse(token));
    //     } catch (RuntimeException e) {
    //         return ResponseEntity.badRequest().body(e.getMessage());
    //     }
    // }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            String token = userService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
            return ResponseEntity.ok(new LoginResponse(token));
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser(Authentication authentication) {
        UserDTO user = userService.getCurrentUser(authentication.getName());
        return ResponseEntity.ok(user);
    }
}
