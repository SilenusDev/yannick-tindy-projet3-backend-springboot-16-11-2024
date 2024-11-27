package com.openclassrooms.api.controllers;

// Imports essentiels
import com.openclassrooms.api.models.ErrorResponse;
import com.openclassrooms.api.models.Rental;
import com.openclassrooms.api.services.RentalService;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
// Collections et Optional
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/rentals")
public class RentalController {
    
    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping
    public ResponseEntity<?> getAllRentals() {
        List<Rental> rentals = rentalService.getAllRentals();
        if (rentals.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("No rentals found.", HttpStatus.NOT_FOUND.value()));
        }
        return ResponseEntity.ok(rentals);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRentalById(@PathVariable Long id) {
        Optional<Rental> rental = rentalService.getRentalById(id);
        if (rental.isPresent()) {
            return ResponseEntity.ok(rental.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Rental with ID " + id + " not found.", 
                            HttpStatus.NOT_FOUND.value()));
        }
    }

    @PostMapping
    public ResponseEntity<?> createRental(@RequestBody Rental rental) {
        try {
            rental.setCreatedAt(LocalDateTime.now());
            rental.setUpdatedAt(LocalDateTime.now());
            
            Rental savedRental = rentalService.createRental(rental);
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

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRental(
        @PathVariable Long id, 
        @RequestBody Rental rentalDetails
    ) {
        try {
            Rental updatedRental = rentalService.updateRental(id, rentalDetails);
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

// @RestController
// @RequestMapping("/rentals")
// public class RentalController {
    
//     private final RentalService rentalService;

//     public RentalController(RentalService rentalService) {
//         this.rentalService = rentalService;
//     }

//     @GetMapping
//     public ResponseEntity<?> getAllRentals() {
//         List<Rental> rentals = rentalService.getAllRentals();
//         if (rentals.isEmpty()) {
//             return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                 .body(new ErrorResponse("No rentals found.", HttpStatus.NOT_FOUND.value()));
//         }
//         return ResponseEntity.ok(rentals);
//     }

//     @PostMapping
//     public ResponseEntity<?> createRental(@RequestBody Rental rental) {
//         try {
//             rental.setCreatedAt(LocalDateTime.now());
//             rental.setUpdatedAt(LocalDateTime.now());
            
//             Rental savedRental = rentalService.createRental(rental);
//             return ResponseEntity.status(HttpStatus.CREATED)
//                 .body(Map.of(
//                     "message", "Rental created successfully", 
//                     "rental", savedRental
//                 ));
//         } catch (Exception e) {
//             return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                 .body(new ErrorResponse(
//                     "Error creating rental: " + e.getMessage(), 
//                     HttpStatus.BAD_REQUEST.value()
//                 ));
//         }
//     }

//     @PutMapping("/{id}")
//     public ResponseEntity<?> updateRental(
//         @PathVariable Long id, 
//         @RequestBody Rental rentalDetails
//     ) {
//         try {
//             Rental updatedRental = rentalService.updateRental(id, rentalDetails);
//             return ResponseEntity.ok()
//                 .body(Map.of(
//                     "message", "Rental updated successfully", 
//                     "rental", updatedRental
//                 ));
//         } catch (Exception e) {
//             return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                 .body(new ErrorResponse(
//                     "Error updating rental: " + e.getMessage(), 
//                     HttpStatus.BAD_REQUEST.value()
//                 ));
//         }
//     }
// }



// public class RentalController {

//     @Autowired
//     private RentalService rentalService;

//     @GetMapping
//     public ResponseEntity<?> getAllRentals() {
//         List<Rental> rentals = rentalService.getAllRentals();
//         if (rentals.isEmpty()) {
//             return ResponseEntity.status(404)
//                 .body(new ErrorResponse("No rentals found.", 404));
//         }
//         return ResponseEntity.ok(rentals);
//     }

//     @GetMapping("/{id}")
//     public ResponseEntity<?> getRentalById(@PathVariable Long id) {
//         Optional<Rental> rental = rentalService.getRentalById(id);
//         if (rental.isPresent()) {
//             return ResponseEntity.ok(rental.get());
//         } else {
//             return ResponseEntity.status(404)
//                     .body(new ErrorResponse("Rental with ID " + id + " not found.", 404));
//         }
//     }
// }

