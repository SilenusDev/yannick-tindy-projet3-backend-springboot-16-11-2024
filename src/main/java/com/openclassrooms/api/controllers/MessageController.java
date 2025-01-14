package com.openclassrooms.api.controllers;

import com.openclassrooms.api.models.Message;
import com.openclassrooms.api.dto.MessageDTO;
import com.openclassrooms.api.models.ErrorResponse;
import com.openclassrooms.api.services.MessageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @Operation(summary = "Get all messages", description = "Retrieves a list of all messages")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Messages retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "404", description = "No messages found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))
    })
    @GetMapping
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.ok(messages);
    }
    
    @Operation(summary = "Create a new message", description = "Creates a new message for a rental")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Message created successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))
    })
    @PostMapping
        public ResponseEntity<?> createMessage(@RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = MessageDTO.class),
            examples = @ExampleObject(
                name = "Message Example",
                value = "{\"rentalId\": 1, \"userId\": 1, \"message\": \"Ceci est un message de test.\", \"createdAt\": \"2025-01-14T11:39:19.2769408\", \"updatedAt\": \"2025-01-14T11:39:19.2769408\"}"
            )
        )
    ) MessageDTO messageDTO) {
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


}
