package com.example.VinylStoreManagement.controller;

import com.example.VinylStoreManagement.dto.CartRequest;
import com.example.VinylStoreManagement.model.Cart;
import com.example.VinylStoreManagement.model.CartItem;
import com.example.VinylStoreManagement.model.Vinyl;
import com.example.VinylStoreManagement.repository.CartItemRepository;
import com.example.VinylStoreManagement.repository.CartRepository;
import com.example.VinylStoreManagement.repository.VinylRepository;
import com.example.VinylStoreManagement.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController// Tells Spring this class will send back JSON data
@RequestMapping("/api/cart")// Every URL in this file will start with /api/cart
public class CartController {
    @Autowired
    private VinylRepository vinylRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private CartService cartService;
    // This handles: POST http://localhost:8080/api/cart/add

    @PostMapping("/add")
    public Cart addToCart(@RequestBody CartRequest request) {
        // We extract the values from the 'request' object
        return cartService.addItemToCart(
                request.getCartId(),
                request.getVinylId(),
                request.getQuantity()
        );
    }
    // This handles: DELETE http://localhost:8080/api/cart/remove/{itemId}
    @DeleteMapping("/remove/{itemId}")
    public ResponseEntity<?> removeFromCart(@PathVariable Long itemId) {
        // 1. Find the item in the cart
        Optional<CartItem> optionalCartItem = cartItemRepository.findById(itemId);

        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();

            // 2. Grab the vinyl associated with this cart item
            Vinyl vinyl = cartItem.getVinyl();

            // 3. RESTORE THE STOCK: Current stock + the amount they had in their cart
            vinyl.setStockQuantity(vinyl.getStockQuantity() + cartItem.getQuantity());

            // 4. Save the updated stock back to the database
            vinylRepository.save(vinyl);

            // 5. Finally, delete the item from the cart
            cartItemRepository.delete(cartItem);

            return ResponseEntity.ok().body("{\"message\": \"Item removed and stock restored\"}");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // This handles: GET http://localhost:8080/api/cart/{cartId}
    @GetMapping("/{cartId}")
//    public Cart getCart(@PathVariable Long cartId) {
//        // We return the whole Cart object (Spring handles the JSON conversion)
//        return cartService.getCartById(cartId);
//    }
    public ResponseEntity<?> getCart(@PathVariable Long cartId, Principal principal) {
        Cart cart = cartService.getCartById(cartId);

        // Checks if the logged-in user's name matches the cart owner's name
        if (!cart.getUser().getUsername().equals(principal.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Access Denied: You cannot view someone else's cart.");
        }

        return ResponseEntity.ok(cart);
    }
}
