//package com.example.VinylStoreManagement.controller;
//
////import com.example.VinylStoreManagement.security.JwtUtils;
//import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.security.authentication.AuthenticationManager;
////import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
////import org.springframework.security.core.Authentication;
////import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/auth")
//public class AuthController {
//
//    @Autowired
//    private AuthenticationManager authenticationManager; // Handles the password check
//
//    @Autowired
//    private JwtUtils jwtUtils; // Our token helper
//
//    @PostMapping("/login")
//    public Map<String, String> authenticateUser(@RequestParam String username, @RequestParam String password) {
//        // 1. Verify the username and password against MySQL
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(username, password));
//
//        // 2. If valid, set the security context
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        // 3. Generate the "Digital Pass" (Token)
//        String jwt = jwtUtils.generateTokenFromUsername(username);
//
//        // 4. Return the token to the user as a JSON object
//        Map<String, String> response = new HashMap<>();
//        response.put("token", jwt);
//        return response;
//    }
//}