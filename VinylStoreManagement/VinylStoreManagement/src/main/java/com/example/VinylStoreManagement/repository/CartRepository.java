package com.example.VinylStoreManagement.repository;

import com.example.VinylStoreManagement.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {
    Cart findByUserId(Long userId);
}
