package com.example.VinylStoreManagement.repository;

import com.example.VinylStoreManagement.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
}
