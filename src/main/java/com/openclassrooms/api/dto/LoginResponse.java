package com.openclassrooms.api.dto;

public class LoginResponse {
    private String token;

    public LoginResponse(String token) {
        this.token = token;
    }

    // Getter uniquement car le token ne devrait pas être modifiable
    public String getToken() {
        return token;
    }
}
