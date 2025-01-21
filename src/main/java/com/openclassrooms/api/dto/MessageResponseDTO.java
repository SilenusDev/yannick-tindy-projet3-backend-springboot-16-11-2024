package com.openclassrooms.api.dto;

public class MessageResponseDTO {
    private String message;

    // Constructeur
    public MessageResponseDTO(String message) {
        this.message = message;
    }

    // Getter
    public String getMessage() {
        return message;
    }
}

