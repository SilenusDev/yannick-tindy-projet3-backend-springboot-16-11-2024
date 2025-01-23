package com.openclassrooms.api.controllers;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.openclassrooms.api.services.RentalService;
import com.openclassrooms.api.models.ErrorResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/images")
@CrossOrigin(origins = "*") // Permettre les requêtes de n'importe quelle origine en développement
public class ImageController {
    private static final String UPLOAD_DIR = "src/upload/";
    private final RentalService rentalService;

    public ImageController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            File file = new File(UPLOAD_DIR + filename);
            if (!file.exists()) {
                return ResponseEntity.notFound().build();
            }
    
            // Détecter le type MIME de l'image en fonction de l'extension du fichier
            String fileExtension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
            MediaType mediaType;
    
            switch (fileExtension) {
                case "jpg":
                case "jpeg":
                    mediaType = MediaType.IMAGE_JPEG;
                    break;
                case "png":
                    mediaType = MediaType.IMAGE_PNG;
                    break;
                default:
                    return ResponseEntity.badRequest().build(); // type de fichier non pris en charge
            }
    
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
    
            return ResponseEntity.ok()
                .contentType(mediaType)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                .body(resource);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    

    @Operation(summary = "Upload rental image", description = "Uploads an image for a rental")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Image uploaded successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))
    })
    @PostMapping(value = "/uploadImage", consumes = {"multipart/form-data"})
    public ResponseEntity<?> uploadImage(@RequestParam("rentalId") Long rentalId,
                                         @RequestPart("imageFile") MultipartFile imageFile) {
        try {
            rentalService.uploadImage(rentalId, imageFile); // Utilisation de l'instance
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of(
                            "message", "Image uploaded successfully"
                    ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(
                            "Error uploading image: " + e.getMessage(),
                            HttpStatus.BAD_REQUEST.value()
                    ));
        }
    }
}
