package com.example.VinylStoreManagement.service;

import com.example.VinylStoreManagement.model.Orders;
import com.example.VinylStoreManagement.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    @Autowired
    private OrdersRepository ordersRepository;
    public Orders processPayment(Long orderId){
        Orders orders=ordersRepository.findById(orderId)
                .orElseThrow(()->new RuntimeException("Order not found with id: " + orderId));
        if("COMPLETED".equals(orders.getStatus())){
            throw new RuntimeException("Order is already paid.");
        }
        orders.setStatus("COMPLETED");
        return ordersRepository.save(orders);
    }
}
