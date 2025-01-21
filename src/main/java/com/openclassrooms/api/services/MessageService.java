package com.openclassrooms.api.services;

import com.openclassrooms.api.models.Message;
import com.openclassrooms.api.repositories.MessageRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    
    @Autowired
    private MessageRepository messageRepository;
    
    @Autowired
    private RentalService rentalService;

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message createMessage(Message message) {
        // Vérification si le rental existe
        if (!rentalService.getRentalById(message.getRentalId()).isPresent()) {
            throw new RuntimeException("Rental not found");
        }

        // Vérification si l'utilisateur existe (optionnel mais recommandé)
        // if (!userService.getUserById(message.getUserId()).isPresent()) {
        //     throw new RuntimeException("User not found");
        // }
        
        // Définir les horodatages
        LocalDateTime dateOnly = LocalDate.now().atStartOfDay();
        message.setCreatedAt(dateOnly);
        message.setUpdatedAt(dateOnly);
        
        return messageRepository.save(message);
    }
}