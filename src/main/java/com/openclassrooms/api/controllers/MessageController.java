package com.openclassrooms.api.controllers;

import com.openclassrooms.api.dto.MessageRequestDTO;
import com.openclassrooms.api.dto.MessageResponseDTO;
import com.openclassrooms.api.services.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<MessageResponseDTO> createMessage(@RequestBody MessageRequestDTO request) {
        MessageResponseDTO response = messageService.createMessage(request);
        return ResponseEntity.ok(response);
    }
}
