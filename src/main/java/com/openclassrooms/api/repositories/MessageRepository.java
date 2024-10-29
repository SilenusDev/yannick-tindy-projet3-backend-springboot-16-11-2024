// MessageRepository.java
package com.openclassrooms.api.repositories;

import com.openclassrooms.api.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    // JpaRepository fournit automatiquement les méthodes CRUD de base comme save(), findById(), etc.
}
