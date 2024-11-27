// MessageController.java
package com.openclassrooms.api.controllers;

import com.openclassrooms.api.models.Message;
import com.openclassrooms.api.dto.MessageDTO;
import com.openclassrooms.api.models.ErrorResponse;
import com.openclassrooms.api.services.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
public class MessageController {
    
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<?> createMessage(@RequestBody MessageDTO messageDTO) {
        // Validation des champs requis
        if (messageDTO.getRentalId() == null || messageDTO.getUserId() == null || 
            messageDTO.getMessage() == null || messageDTO.getMessage().trim().isEmpty()) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("Missing required fields: rental_id, user_id, and message are required.", 
                        HttpStatus.BAD_REQUEST.value()));
        }

        try {
            // Convertir DTO en entité
            Message message = new Message();
            message.setRentalId(messageDTO.getRentalId());
            message.setUserId(messageDTO.getUserId());
            message.setMessage(messageDTO.getMessage());

            Message createdMessage = messageService.createMessage(message);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdMessage);
        } catch (RuntimeException e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    // @PostMapping
    // public ResponseEntity<?> createMessage(@RequestBody Message message) {
    //     // Validation des champs requis
    //     if (message.getRentalId() == null || message.getUserId() == null || 
    //         message.getMessage() == null || message.getMessage().trim().isEmpty()) {
    //         return ResponseEntity
    //             .status(HttpStatus.BAD_REQUEST)
    //             .body(new ErrorResponse("Missing required fields: rental_id, user_id, and message are required.", 
    //                     HttpStatus.BAD_REQUEST.value()));
    //     }

    //     try {
    //         Message createdMessage = messageService.createMessage(message);
    //         return ResponseEntity.status(HttpStatus.CREATED).body(createdMessage);
    //     } catch (RuntimeException e) {
    //         return ResponseEntity
    //             .status(HttpStatus.BAD_REQUEST)
    //             .body(new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
    //     }
    // }
}
