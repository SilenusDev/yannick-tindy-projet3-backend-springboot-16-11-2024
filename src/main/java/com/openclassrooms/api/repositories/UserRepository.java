package com.openclassrooms.api.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.openclassrooms.api.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
