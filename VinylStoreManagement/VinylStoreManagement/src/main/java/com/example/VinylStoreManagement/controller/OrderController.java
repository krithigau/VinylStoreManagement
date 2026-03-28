package com.example.VinylStoreManagement.controller;

import com.example.VinylStoreManagement.model.Orders;
import com.example.VinylStoreManagement.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping("/checkout/{cartId}")
    public ResponseEntity<Orders>  checkout(@PathVariable Long cartId){
        try{
            Orders order=orderService.checkout(cartId);
            return new ResponseEntity<>(order, HttpStatus.CREATED);
        }catch(RuntimeException e){
            //e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }
    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable Long orderId, Principal principal) {
        Orders order = orderService.getOrderById(orderId);

        if (!order.getUser().getUsername().equals(principal.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Access Denied: You cannot view someone else's order history.");
        }

        return ResponseEntity.ok(order);
    }
}
