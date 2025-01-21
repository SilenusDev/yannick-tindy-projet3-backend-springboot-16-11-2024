package com.openclassrooms.api.repositories;

import com.openclassrooms.api.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
