package com.openclassrooms.api.dto;

public class MessageDTO {
    private Long rentalId;
    private Long userId;
    private String message;

    public MessageDTO(Long rentalId, Long userId, String message) {
        this.rentalId = rentalId;
        this.userId = userId;
        this.message = message;
    }

    // Getters et Setters
    public Long getRentalId() {
        return rentalId;
    }

    public void setRentalId(Long rentalId) {
        this.rentalId = rentalId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
