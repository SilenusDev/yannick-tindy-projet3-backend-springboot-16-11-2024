package com.openclassrooms.api.dto;

public class UserDTO {
    private Long id;
    private String email;
    private String name;
    private String role;

    public UserDTO(Long id, String email, String name, String role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.role = role;
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
    public String getRole() { // Getter pour role
        return role;
    }

    public void setRole(String role) { // Setter pour role
        this.role = role;
    }
}
