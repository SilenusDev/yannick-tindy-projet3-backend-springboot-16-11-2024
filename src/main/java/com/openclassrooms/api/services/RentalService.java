package com.openclassrooms.api.services;

import com.openclassrooms.api.models.Rental;
import com.openclassrooms.api.dto.RentalDTO;
import com.openclassrooms.api.repositories.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final ImageUploadService imageUploadService;

    public RentalService(RentalRepository rentalRepository, ImageUploadService imageUploadService) {
        this.rentalRepository = rentalRepository;
        this.imageUploadService = imageUploadService;
    }

    public List<RentalDTO> getAllRentals() {
        List<Rental> rentals = rentalRepository.findAll();
        return rentals.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Optional<RentalDTO> getRentalById(Long id) {
        Optional<Rental> rental = rentalRepository.findById(id);
        return rental.map(this::convertToDTO);
    }

    public RentalDTO createRental(RentalDTO rentalDTO) throws IOException {
        MultipartFile imageFile = rentalDTO.getImageFile();
        if (imageFile != null && !imageFile.isEmpty()) {
            String imagePath = imageUploadService.uploadImage(imageFile);
            rentalDTO.setPicture(imagePath);
        }

        Rental rental = convertToEntity(rentalDTO);
        Rental savedRental = rentalRepository.save(rental);
        return convertToDTO(savedRental);
    }

    public RentalDTO updateRental(Long id, RentalDTO rentalDetails) {
        Optional<Rental> optionalRental = rentalRepository.findById(id);
        if (optionalRental.isPresent()) {
            Rental rental = optionalRental.get();
            rental.setName(rentalDetails.getName());
            rental.setSurface(rentalDetails.getSurface());
            rental.setPrice(rentalDetails.getPrice());
            rental.setPicture(rentalDetails.getPicture());
            rental.setDescription(rentalDetails.getDescription());
            rental.setOwnerId(rentalDetails.getOwnerId());
            LocalDateTime dateOnly = LocalDate.now().atStartOfDay();
            rental.setCreatedAt(dateOnly);
            rental.setUpdatedAt(dateOnly);

            Rental updatedRental = rentalRepository.save(rental);
            return convertToDTO(updatedRental);
        } else {
            throw new IllegalArgumentException("Rental with ID " + id + " not found.");
        }
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