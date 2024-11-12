package com.openclassrooms.api.controllers;

import com.openclassrooms.api.models.User;
import com.openclassrooms.api.repositories.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    // Constructeur pour injecter UserRepository
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Route GET /users pour obtenir tous les utilisateurs
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}

