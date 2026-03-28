package com.example.VinylStoreManagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime; // Correct Java Date import
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name="orders")
public class Orders {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    private LocalDateTime orderDate;
    private Double totalAmount;
    private String status;// e.g., "PENDING", "SHIPPED"
    @OneToMany(mappedBy="order",cascade=CascadeType.ALL)
    private List<OrderItem> orderItems=new ArrayList<>();

}
