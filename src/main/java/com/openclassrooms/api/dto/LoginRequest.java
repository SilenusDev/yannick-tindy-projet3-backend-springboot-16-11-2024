package com.openclassrooms.api.dto;

public class LoginRequest {
    private String email;
    private String password;

    // Constructeur vide (nécessaire pour la désérialisation JSON)
    public LoginRequest() {}

    // Getters et Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
