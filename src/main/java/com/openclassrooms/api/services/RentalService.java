package com.openclassrooms.api.services;

import com.openclassrooms.api.models.Rental;
import com.openclassrooms.api.repositories.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RentalService {
    
    @Autowired
    private RentalRepository rentalRepository;

    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    public Optional<Rental> getRentalById(Long id) {
        return rentalRepository.findById(id);
    }

    public Rental createRental(Rental rental) {
        // Ici, vous devrez ajouter la logique d'authentification et de validation
        rental.setCreatedAt(LocalDateTime.now());
        rental.setUpdatedAt(LocalDateTime.now());
        return rentalRepository.save(rental);
    }
    
    public Rental updateRental(Long id, Rental rentalDetails) {
        Rental existingRental = rentalRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Rental not found"));
        
        // Mettre à jour tous les champs
        existingRental.setName(rentalDetails.getName());
        existingRental.setSurface(rentalDetails.getSurface());
        existingRental.setPrice(rentalDetails.getPrice());
        existingRental.setPicture(rentalDetails.getPicture());
        existingRental.setDescription(rentalDetails.getDescription());
        existingRental.setOwnerId(rentalDetails.getOwnerId());
        existingRental.setUpdatedAt(LocalDateTime.now());
    
        return rentalRepository.save(existingRental);
    }
}
