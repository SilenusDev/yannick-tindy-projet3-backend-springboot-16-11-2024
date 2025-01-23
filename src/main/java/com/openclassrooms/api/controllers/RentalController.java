package com.openclassrooms.api.controllers;

import com.openclassrooms.api.models.ErrorResponse;
import com.openclassrooms.api.dto.RentalDTO;
import com.openclassrooms.api.services.RentalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @Operation(summary = "Get all rentals", description = "Retrieves a list of all rentals")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rentals retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RentalDTO.class))),
        @ApiResponse(responseCode = "404", description = "No rentals found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))
    })
    @GetMapping
    public ResponseEntity<?> getAllRentals() {
        List<RentalDTO> rentals = rentalService.getAllRentals();
        if (rentals.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("No rentals found.", HttpStatus.NOT_FOUND.value()));
        }
        return ResponseEntity.ok(Map.of("rentals", rentals));
    }

    @Operation(summary = "Get rental by ID", description = "Retrieves a rental by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rental retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RentalDTO.class))),
        @ApiResponse(responseCode = "404", description = "Rental not found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getRentalById(@PathVariable Long id) {
        Optional<RentalDTO> rental = rentalService.getRentalById(id);
        if (rental.isPresent()) {
            return ResponseEntity.ok(rental.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Rental with ID " + id + " not found.",
                            HttpStatus.NOT_FOUND.value()));
        }
    }

    @Operation(summary = "Create a new rental", description = "Creates a new rental")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Rental created successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RentalDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))
    })
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<?> createRental(@RequestParam("name") String name,
                                          @RequestParam("surface") BigDecimal surface,
                                          @RequestParam("price") BigDecimal price,
                                          @RequestParam("description") String description,
                                          @RequestParam("ownerId") Long ownerId,
                                          @RequestPart("imageFile") MultipartFile imageFile) {
        try {
            RentalDTO rentalDTO = new RentalDTO();
            rentalDTO.setName(name);
            rentalDTO.setSurface(surface);
            rentalDTO.setPrice(price);
            rentalDTO.setDescription(description);
            rentalDTO.setOwner_id(ownerId);
            rentalDTO.setImageFile(imageFile); // Ajoutez cette ligne
    
            RentalDTO savedRental = rentalService.createRental(rentalDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of(
                            "message", "Rental created successfully",
                            "rental", savedRental
                    ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(
                            "Error creating rental: " + e.getMessage(),
                            HttpStatus.BAD_REQUEST.value()
                    ));
        }
    }
    

    @Operation(summary = "Update a rental", description = "Updates an existing rental by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rental updated successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RentalDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRental(
            @PathVariable Long id,
            @RequestBody RentalDTO rentalDetails
    ) {
        try {
            RentalDTO updatedRental = rentalService.updateRental(id, rentalDetails);
            return ResponseEntity.ok()
                    .body(Map.of(
                            "message", "Rental updated successfully",
                            "rental", updatedRental
                    ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(
                            "Error updating rental: " + e.getMessage(),
                            HttpStatus.BAD_REQUEST.value()
                    ));
        }
    }
}

