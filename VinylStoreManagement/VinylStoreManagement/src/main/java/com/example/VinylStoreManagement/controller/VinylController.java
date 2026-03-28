package com.example.VinylStoreManagement.controller;

import com.example.VinylStoreManagement.repository.VinylRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import com.example.VinylStoreManagement.model.Vinyl;
import com.example.VinylStoreManagement.service.VinylService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vinyls")
public class VinylController {
    @Autowired
    private VinylRepository vinylRepository;
    @Autowired
    private VinylService vinylService;

    @GetMapping
    public List<Vinyl> getAll(){
        return vinylService.getAllVinyl();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Vinyl create(@RequestBody Vinyl vinyl){
        return vinylService.addVinly(vinyl);
    }
    // ==========================================
    // 1. UPDATE (EDIT) AN EXISTING VINYL
    // ==========================================
    @PutMapping("/{id}")
    public ResponseEntity<Vinyl> updateVinyl(@PathVariable Long id, @RequestBody Vinyl vinylDetails) {
        // Find the vinyl in the database
        Optional<Vinyl> optionalVinyl = vinylRepository.findById(id);

        if (optionalVinyl.isPresent()) {
            Vinyl existingVinyl = optionalVinyl.get();

            // Update all the fields with the new data from React
            existingVinyl.setTitle(vinylDetails.getTitle());
            existingVinyl.setArtist(vinylDetails.getArtist());
            existingVinyl.setPrice(vinylDetails.getPrice());
            existingVinyl.setStockQuantity(vinylDetails.getStockQuantity());
            existingVinyl.setImageUrl(vinylDetails.getImageUrl());

            // Save the updated record back to MySQL
            Vinyl updatedVinyl = vinylRepository.save(existingVinyl);
            return ResponseEntity.ok(updatedVinyl);
        } else {
            // If the ID doesn't exist, return a 404 Not Found
            return ResponseEntity.notFound().build();
        }
    }

    // ==========================================
    // 2. DELETE A VINYL FROM THE VAULT
    // ==========================================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVinyl(@PathVariable Long id) {
        // Find the vinyl first to make sure it exists
        Optional<Vinyl> optionalVinyl = vinylRepository.findById(id);

        if (optionalVinyl.isPresent()) {
            // Delete it from MySQL
            vinylRepository.delete(optionalVinyl.get());
            return ResponseEntity.ok().build(); // Return 200 OK (Success)
        } else {
            return ResponseEntity.notFound().build(); // Return 404 if not found
        }
    }

}