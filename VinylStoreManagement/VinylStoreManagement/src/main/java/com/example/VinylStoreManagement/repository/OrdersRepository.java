package com.example.VinylStoreManagement.repository;

import com.example.VinylStoreManagement.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders,Long> {
}
