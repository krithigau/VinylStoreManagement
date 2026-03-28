package com.example.VinylStoreManagement.controller;

import com.example.VinylStoreManagement.model.Orders;
import com.example.VinylStoreManagement.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    @PostMapping("/pay/{orderId}")
    public ResponseEntity<?> payOrder(@PathVariable Long orderId){
        try{
            Orders updatedOrder=paymentService.processPayment(orderId);
            return ResponseEntity.ok(updatedOrder);
        }catch(RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage()) ;       }
    }
}
