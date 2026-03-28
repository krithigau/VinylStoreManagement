package com.example.VinylStoreManagement.repository;

import com.example.VinylStoreManagement.model.Vinyl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VinylRepository extends JpaRepository<Vinyl,Long> {
}
