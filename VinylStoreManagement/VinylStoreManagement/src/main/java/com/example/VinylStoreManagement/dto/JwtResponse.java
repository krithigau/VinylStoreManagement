package com.example.VinylStoreManagement.dto;

public class JwtResponse {
    private String token;
    private Long userId;
    private Long cartId;
    private String role;
    // Constructor to hold all three pieces of data
    public JwtResponse(String token, Long userId, Long cartId,String role) {
        this.token = token;
        this.userId = userId;
        this.cartId = cartId;
        this.role=role;
    }
    public  String getRole(){
        return role;
    }
    // Getters and Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getCartId() { return cartId; }
    public void setCartId(Long cartId) { this.cartId = cartId; }
}