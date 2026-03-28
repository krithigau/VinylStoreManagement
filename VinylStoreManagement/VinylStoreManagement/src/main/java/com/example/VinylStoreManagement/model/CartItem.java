package com.example.VinylStoreManagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name="cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="cart_id")// Links to the Cart// We want a physical column named 'cart_id' here
    @JsonIgnore//to prevent infinite recursion loop in getting the cart details
    private Cart cart;

    @ManyToOne
    @JoinColumn(name="vinyl_id")// Links to the Vinyl record
    private Vinyl vinyl;
    private int quantity;

}
