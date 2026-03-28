package com.example.VinylStoreManagement.dto;

public class CartRequest {
    private Long cartId;
    private Long vinylId;
    private int quantity;

    // Getters and Setters (Important!)
    public Long getCartId() { return cartId; }
    public void setCartId(Long cartId) { this.cartId = cartId; }
    public Long getVinylId() { return vinylId; }
    public void setVinylId(Long vinylId) { this.vinylId = vinylId; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}