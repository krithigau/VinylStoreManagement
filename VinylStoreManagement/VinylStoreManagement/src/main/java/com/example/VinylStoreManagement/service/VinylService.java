package com.example.VinylStoreManagement.service;

import com.example.VinylStoreManagement.exception.ResourceNotFoundException;
import com.example.VinylStoreManagement.model.Vinyl;
import com.example.VinylStoreManagement.repository.VinylRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VinylService {
    @Autowired // This automatically connects your VinylRepository here
    private VinylRepository vinylRepository;
    // 1. Logic to get all vinyls
    public List<Vinyl> getAllVinyl(){
        return vinylRepository.findAll();
    }
    // 2. Logic to add a new vinyl
    public Vinyl addVinly (Vinyl vinyl){
        return vinylRepository.save(vinyl);
    }
    // 3. Logic to find one vinyl by its ID
    public Vinyl getVinylById(Long id){
        return vinylRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Vinyl not found with id:"+id));
    }
}
