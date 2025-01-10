package com.openclassrooms.api.services;

import com.openclassrooms.api.models.Rental;
import com.openclassrooms.api.dto.RentalDTO;
import com.openclassrooms.api.repositories.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    public List<RentalDTO> getAllRentals() {
        List<Rental> rentals = rentalRepository.findAll();
        return rentals.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<RentalDTO> getRentalById(Long id) {
        Optional<Rental> rental = rentalRepository.findById(id);
        return rental.map(this::convertToDTO);
    }

    public RentalDTO createRental(RentalDTO rentalDTO) {
        Rental rental = convertToEntity(rentalDTO);
        rental.setCreatedAt(LocalDateTime.now());
        rental.setUpdatedAt(LocalDateTime.now());
        Rental savedRental = rentalRepository.save(rental);
        return convertToDTO(savedRental);
    }

    public RentalDTO updateRental(Long id, RentalDTO rentalDetails) {
        Rental existingRental = rentalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rental not found"));

        existingRental.setName(rentalDetails.getName());
        existingRental.setSurface(rentalDetails.getSurface());
        existingRental.setPrice(rentalDetails.getPrice());
        existingRental.setPicture(rentalDetails.getPicture());
        existingRental.setDescription(rentalDetails.getDescription());
        existingRental.setOwnerId(rentalDetails.getOwnerId());
        existingRental.setUpdatedAt(LocalDateTime.now());

        Rental updatedRental = rentalRepository.save(existingRental);
        return convertToDTO(updatedRental);
    }

    private RentalDTO convertToDTO(Rental rental) {
        RentalDTO rentalDTO = new RentalDTO();
        rentalDTO.setId(rental.getId());
        rentalDTO.setName(rental.getName());
        rentalDTO.setSurface(rental.getSurface());
        rentalDTO.setPrice(rental.getPrice());
        rentalDTO.setPicture(rental.getPicture());
        rentalDTO.setDescription(rental.getDescription());
        rentalDTO.setOwnerId(rental.getOwnerId());
        rentalDTO.setCreatedAt(rental.getCreatedAt());
        rentalDTO.setUpdatedAt(rental.getUpdatedAt());
        return rentalDTO;
    }

    private Rental convertToEntity(RentalDTO rentalDTO) {
        Rental rental = new Rental();
        rental.setName(rentalDTO.getName());
        rental.setSurface(rentalDTO.getSurface());
        rental.setPrice(rentalDTO.getPrice());
        rental.setPicture(rentalDTO.getPicture());
        rental.setDescription(rentalDTO.getDescription());
        rental.setOwnerId(rentalDTO.getOwnerId());
        return rental;
    }
}