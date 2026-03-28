package com.example.VinylStoreManagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalDateTime; // Correct Java Date import
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Orders order;// used in child class

    private String paymentMethod;// "UPI", "CARD"
    private String paymentStatus;// "SUCCESS", "FAILED"
    private LocalDateTime paymentDate;
    private Double amount;// The actual money paid
}
