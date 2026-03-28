package com.example.VinylStoreManagement.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@Data // Handles Getters, Setters, ToString
@NoArgsConstructor // Required by JPA (Hibernate)
@AllArgsConstructor
@Table(name="users")// This gives the table a name in MySQL
//@Setter
//@Getter
public class User {

    @Id// Every table needs a unique ID (Primary Key)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false,unique=true)
    private String username;
    @Column(nullable = false)
    private String password;
    //@Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String role;// e.g., "ROLE_USER" or "ROLE_ADMIN"
    @OneToOne(mappedBy="user",cascade= CascadeType.ALL)
    @JsonManagedReference //"I am the boss, start here"
    private Cart cart;

   }
