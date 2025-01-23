package com.openclassrooms.api.dto;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RentalDTO {
    private Long id;
    private String name;
    private BigDecimal surface;
    private BigDecimal price;
    private String picture;
    private String description;
    private Long owner_id;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    // private MultipartFile imageFile; // Ajoutez cette ligne

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getSurface() {
        return surface;
    }

    public void setSurface(Double surface) {
        this.surface = surface != null ? BigDecimal.valueOf(surface) : null;
    }
    // public void setSurface(BigDecimal surface) {
    //     this.surface = surface;
    // }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price != null ? BigDecimal.valueOf(price) : null;
    }
    // public void setPrice(BigDecimal price) {
    //     this.price = price;
    // }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(Long owner_id) {
        this.owner_id = owner_id;
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

    // public MultipartFile getImageFile() {
    //     return imageFile;
    // }

    // public void setImageFile(MultipartFile imageFile) {
    //     this.imageFile = imageFile;
    // }
}
