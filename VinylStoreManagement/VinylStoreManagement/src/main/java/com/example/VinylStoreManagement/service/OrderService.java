package com.example.VinylStoreManagement.service;

import com.example.VinylStoreManagement.model.*;
import com.example.VinylStoreManagement.repository.CartRepository;
import com.example.VinylStoreManagement.repository.OrdersRepository;
import com.example.VinylStoreManagement.repository.VinylRepository;
import jakarta.transaction.Transactional;
import org.hibernate.query.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private VinylRepository vinylRepository;
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private CartRepository cartRepository;
    @Transactional // Protects our data if something crashes mid-checkout!
    //==========
    public Orders getOrderById(Long orderId) {
        return ordersRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
    }
    //=========

    public Orders checkout(Long cartId){

        // 1. Find the cart
        Cart cart=cartRepository.findById(cartId)
                .orElseThrow(()->new RuntimeException("Cart not found with id:"+cartId));
        // 2. Validate the cart isn't empty
        if(cart.getItems()==null || cart.getItems().isEmpty()){
            throw new RuntimeException("Cannot checkout. Your cart is empty!");}
        // 3. Create the new permanent Order
        Orders order=new Orders();
        order.setUser(cart.getUser());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");
        // 4. Copy CartItems into OrderItems and calculate total price manually
        List<OrderItem> orderItems=new ArrayList<>();
        double calculatedTotal=0.0;
        for (CartItem cartItem:cart.getItems()) {
            OrderItem orderItem=new OrderItem();
            Vinyl vinyl =cartItem.getVinyl();
            if(vinyl.getStockQuantity()<cartItem.getQuantity()){
                throw new RuntimeException("Not enough stock for: " + vinyl.getTitle());
            }
            vinyl.setStockQuantity((vinyl.getStockQuantity())-cartItem.getQuantity());
            vinylRepository.save(vinyl);
            orderItem.setOrder(order);
            orderItem.setVinyl(cartItem.getVinyl());
            orderItem.setQuantity(cartItem.getQuantity());
            double itemTotal=cartItem.getQuantity()*cartItem.getVinyl().getPrice();
            orderItem.setPriceAtPurchase(itemTotal);
            calculatedTotal+=itemTotal;
            orderItems.add(orderItem);

        }
        order.setOrderItems(orderItems);
        order.setTotalAmount(calculatedTotal);
        // 5. Save the permanent order to the database
        Orders savedOrder=ordersRepository.save(order);
        // 6. Empty the user's cart for their next visit (UPDATED to getItems)
        cart.getItems().clear();
        cartRepository.save(cart);
        return savedOrder;

    }

}
