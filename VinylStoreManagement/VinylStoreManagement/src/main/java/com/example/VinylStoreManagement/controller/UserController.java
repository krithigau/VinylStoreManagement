package com.example.VinylStoreManagement.controller;

import com.example.VinylStoreManagement.model.Cart;
import com.example.VinylStoreManagement.model.User;
import com.example.VinylStoreManagement.repository.CartRepository;
import com.example.VinylStoreManagement.repository.UserRepository;
import com.example.VinylStoreManagement.service.CartService;
import com.example.VinylStoreManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.VinylStoreManagement.dto.LoginRequest;
import com.example.VinylStoreManagement.dto.JwtResponse;
import com.example.VinylStoreManagement.security.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    //===========
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository; // <-- Notice the lowercase 'u' on the second word!
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CartService cartService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private CartRepository cartRepository;
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        // Fetch the user from the database to get their actual ID
        User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow();

        // Fetch the cart linked to this user
        Cart userCart = cartRepository.findByUserId(user.getId());

        // If for some reason an old user doesn't have a cart, create one now
        if (userCart == null) {
            userCart = new Cart();
            userCart.setUser(user);
            cartRepository.save(userCart);
        }

        // Return all three values
        return ResponseEntity.ok(new JwtResponse(jwt, user.getId(), userCart.getId(),
                user.getRole()));
    }
    //==============
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().body("Username already taken!");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // THE FIX: Assign a default role if one isn't provided
        if (user.getRole() == null) {
            user.setRole("CUSTOMER"); // Or "ROLE_USER" depending on your security setup
        }

        User savedUser = userRepository.save(user);
        cartService.createCartForUser(savedUser);

        return ResponseEntity.ok("User registered successfully!");
    }
//    @PostMapping("/register")
//    public User register(@RequestBody User user){
//        return userService.registerUser(user);
//    }
    // This handles: GET http://localhost:8080/api/users/1
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        User user=userService.getUserById(id);
        return ResponseEntity.ok(user);
    }
    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }
}
