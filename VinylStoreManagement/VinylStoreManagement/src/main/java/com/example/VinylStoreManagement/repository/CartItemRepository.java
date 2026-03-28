package com.example.VinylStoreManagement.repository;

import com.example.VinylStoreManagement.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
}
