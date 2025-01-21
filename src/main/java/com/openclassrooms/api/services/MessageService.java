package com.openclassrooms.api.services;

import com.openclassrooms.api.dto.MessageRequestDTO;
import com.openclassrooms.api.dto.MessageResponseDTO;
import com.openclassrooms.api.models.Message;
import com.openclassrooms.api.repositories.MessageRepository;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public MessageResponseDTO createMessage(MessageRequestDTO request) {
        Message message = new Message();
        message.setRentalId(request.getRentalId());
        message.setUserId(request.getUserId());
        message.setMessage(request.getMessage());

        message = messageRepository.save(message);

        return new MessageResponseDTO("Message created successfully with ID: " + message.getId());
    }
}
