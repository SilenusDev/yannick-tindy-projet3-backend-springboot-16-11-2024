package com.openclassrooms.api.controllers;

// Imports essentiels
import com.openclassrooms.api.models.ErrorResponse;
import com.openclassrooms.api.models.Rental;
import com.openclassrooms.api.services.RentalService;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Collections et Optional
import java.util.List;
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
}
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

