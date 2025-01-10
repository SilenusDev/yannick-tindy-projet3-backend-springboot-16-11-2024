package com.openclassrooms.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.openclassrooms.api.dto.RegisterRequest;
import com.openclassrooms.api.dto.UserDTO;
import com.openclassrooms.api.services.UserService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @ApiResponse(responseCode = "200", description = "You're registered")
    @ApiResponse(responseCode = "500", description = "register error")
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
                request.getPassword()
            );
            
            // Retourner directement le token
            return ResponseEntity.ok(token);
            
        } catch (Exception e) {
            // Log l'erreur pour le débogage
            System.out.println("Erreur lors de l'enregistrement: " + e.getMessage());
            
            // Retourner un message d'erreur plus spécifique
            return ResponseEntity.badRequest()
                .body("Erreur lors de l'enregistrement: " + e.getMessage());
        }
    }

    @ApiResponse(responseCode = "200", description = "You're logged in")
    @ApiResponse(responseCode = "500", description = "Loging error")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");
    
        try {
            String token = userService.authenticate(email, password);
            return ResponseEntity.ok(java.util.Collections.singletonMap("token", token));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(java.util.Collections.singletonMap("error", "Invalid credentials"));
        }
    }

    @ApiResponse(responseCode = "200", description = "Welcome")
    @ApiResponse(responseCode = "500", description = "Owner error")
    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser(Authentication authentication) {
        UserDTO user = userService.getCurrentUser(authentication.getName());
        return ResponseEntity.ok(user);
    }
}





