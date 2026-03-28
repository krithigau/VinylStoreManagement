package com.example.VinylStoreManagement.repository;

import com.example.VinylStoreManagement.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
}
