package com.original.ecommerce.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.original.ecommerce.entity.Orders;
import com.original.ecommerce.repository.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }

    

    public void updateOrderStatus(Long orderId, String status) {
        Orders order = orderRepository.findById(orderId)
            .orElseThrow(() -> new NoSuchElementException("Order not found with ID: " + orderId));
        order.setStatus(status);
        orderRepository.save(order);
    }
}