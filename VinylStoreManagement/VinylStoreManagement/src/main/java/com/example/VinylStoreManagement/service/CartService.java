package com.example.VinylStoreManagement.service;

import com.example.VinylStoreManagement.exception.ResourceNotFoundException;
import com.example.VinylStoreManagement.model.Cart;
import com.example.VinylStoreManagement.model.CartItem;
import com.example.VinylStoreManagement.model.User;
import com.example.VinylStoreManagement.model.Vinyl;
import com.example.VinylStoreManagement.repository.CartItemRepository;
import com.example.VinylStoreManagement.repository.CartRepository;
import com.example.VinylStoreManagement.repository.UserRepository;
import com.example.VinylStoreManagement.repository.VinylRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    @Autowired// Automatically connects the Repository so we can talk to MySQL
    private CartRepository cartRepository;

    @Autowired// We need this to verify the Vinyl exists before adding it
    private VinylRepository vinylRepository;

    @Autowired// This is where we will actually SAVE the new cart item
    private CartItemRepository cartItemRepository;
    @Transactional // Added this to ensure both Cart and Stock update together
    public Cart addItemToCart(long cartId, Long vinylId, int quantity) {
        // 1. Fetch the Cart
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        // 2. Fetch the Vinyl
        Vinyl vinyl = vinylRepository.findById(vinylId)
                .orElseThrow(() -> new ResourceNotFoundException("Vinyl not found"));

        // 3. Check Stock
        if (vinyl.getStockQuantity() < quantity) {
            throw new RuntimeException("Only " + vinyl.getStockQuantity() + " copies of " + vinyl.getTitle() + " left in stock!");
        }

        // --- THE FIX: UPDATE THE STOCK ---
        // 4. Subtract the quantity from the current stock
        int updatedStock = vinyl.getStockQuantity() - quantity;
        vinyl.setStockQuantity(updatedStock);

        // 5. Save the updated Vinyl back to the database
        vinylRepository.save(vinyl);

        // 6. Create the "Bridge" (CartItem)
        CartItem newItem = new CartItem();
        newItem.setCart(cart);
        newItem.setVinyl(vinyl);
        newItem.setQuantity(quantity);

        // 7. Save the item and return the updated cart
        cartItemRepository.save(newItem);

        return cart;
    }
    public void removeItemFromCart(Long itemId){
        CartItem item=cartItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found in cart"));
        cartItemRepository.delete(item);
    }
    // Line-by-line: We just fetch the cart object which already contains the items
    public Cart getCartById(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
    }
    // Inside CartService class
    public void createCartForUser(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        // This saves a new row in the 'carts' table linked to the new user
        cartRepository.save(cart);
    }


    }
