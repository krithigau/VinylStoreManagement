package com.example.VinylStoreManagement.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name="carts")
public class Cart {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")// This creates the Foreign Key column
    @JsonBackReference // "Don't look back at the user from here"
    private User user;
    @OneToMany(mappedBy ="cart",cascade=CascadeType.ALL)
    private List<CartItem> items;
}

