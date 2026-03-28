package com.example.VinylStoreManagement.model;

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
@Table(name="vinyls")
public class Vinyl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //This is the "vinyl_id"
    private String title;  // Album name
    private String artist;  // Band name
    //private String genre;
    private Double price; // How much it costs
    private int stockQuantity;  // How many are left in the store
    // Add this annotation right above the imageUrl
    @Column(length = 2048)
    private String imageUrl;
}
