package com.example.VinylStoreManagement.service;

import com.example.VinylStoreManagement.model.Cart;
import com.example.VinylStoreManagement.model.User;
import com.example.VinylStoreManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user){
        // 1. Scramble the password so the Bouncer is happy (NEW)
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Cart cart=new Cart();
        cart.setUser(user);
        user.setCart(cart);
        user.setRole("CUSTOMER");
        return userRepository.save(user);
    }
    public User getUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("User not found with id: " + id));
    }
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

}
