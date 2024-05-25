package com.app.shopping.service.impl;

import com.app.shopping.dto.Item;
import com.app.shopping.dto.Orders;
import com.app.shopping.repository.OrderRepo;
import com.app.shopping.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepo orderRepo;

    @Override
    public Long placeOrder(Orders order) {
        try {
            double totalPrice = calculateTotalPrice(order);
            if (totalPrice > 0) {
                order.setTotalPrice(totalPrice);
                order.setStatus("Order Placed");
            } else {
                System.out.println("Price should not be zero");
                return null;
            }
            return orderRepo.saveAndFlush(order).getOrderId();
        } catch (Exception e) {
            System.out.println("An error occurred while placing the order: " + e.getMessage());
            return null;
        }
    }

    private double calculateTotalPrice(Orders order) {
        double totalPrice = 0;
        for (Item item : order.getOrderItems()) {
            double price = item.getPrice();
            if (price != 0) {
                totalPrice += item.getQuantity() * price;
            } else {
                return 0; // Price should not be zero
            }
        }
        return totalPrice;
    }

    @Override
    public Orders getOrderByID(Long orderId) {
        try {
            return orderRepo.findById(orderId).orElse(null);
        } catch (Exception e) {
            System.out.println("An error occurred while retrieving the order: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Orders> getAllOrders() {
        try {
            return orderRepo.findAll();
        } catch (Exception e) {
            System.out.println("An error occurred while retrieving the orders: " + e.getMessage());
            return null;
        }
    }

    @Override
    public String cancelOrder(Long orderId) {
        try {
            Optional<Orders> optionalOrder = orderRepo.findById(orderId);
            if (optionalOrder.isPresent()) {
                Orders order = optionalOrder.get();
                String currentStatus = order.getStatus();
                if (currentStatus != null && !currentStatus.isEmpty()) {
                    order.setStatus("Order Cancelled");
                    return orderRepo.save(order).getStatus();
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred while cancelling the order: " + e.getMessage());
        }
        return null;
    }

    @Override
    public String deleteOrder(Long orderId) {
        try {
            Optional<Orders> optionalOrder = orderRepo.findById(orderId);
            if (optionalOrder.isPresent()) {
                Orders order = optionalOrder.get();
                if (order != null) {
                    order.setStatus("Order Deleted!");
                    orderRepo.delete(order);
                    return "Order Deleted Successfully!";
                }
            } else {
                return "No order present for the order ID " + orderId;
            }
        } catch (Exception e) {
            System.out.println("An error occurred while deleting the order: " + e.getMessage());
        }
        return null;
    }
}

