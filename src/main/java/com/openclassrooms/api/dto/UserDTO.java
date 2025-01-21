package com.openclassrooms.api.dto;

import java.time.LocalDateTime;
import com.openclassrooms.api.models.User;

public class UserDTO {
    private Long id;
    private String email;
    private String name;
    private String role;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    // Constructeur avec tous les champs
    public UserDTO(Long id, String email, String name, String role, LocalDateTime created_at, LocalDateTime updated_at) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.role = role;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    // Méthode de conversion
    public static UserDTO fromEntity(User user) {
        return new UserDTO(
            user.getId(),
            user.getEmail(),
            user.getName(),
            user.getRole(),
            user.getCreated_at(),
            user.getUpdated_at()
        );
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }
}
