package com.openclassrooms.api.dto;

import com.openclassrooms.api.models.User;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String email;
    private String name;
    
    // Ne pas inclure le password dans la réponse
    public UserDTO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
    }
}
