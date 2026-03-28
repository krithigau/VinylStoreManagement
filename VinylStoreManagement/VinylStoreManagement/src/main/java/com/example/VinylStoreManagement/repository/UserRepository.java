package com.example.VinylStoreManagement.repository;

import com.example.VinylStoreManagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // Tells Spring this is a Data Access component
public interface UserRepository extends JpaRepository<User,Long> {
    // Spring Data JPA magically writes the SQL for this based on the method name!
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
