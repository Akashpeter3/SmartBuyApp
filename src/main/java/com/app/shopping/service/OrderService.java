package com.app.shopping.service;

import com.app.shopping.dto.Orders;

import java.util.List;

public interface OrderService {

    Long placeOrder(Orders orders);

    Orders getOrderByID(Long orderId);


    List<Orders> getAllOrders();

    String cancelOrder(Long orderID);

    String deleteOrder(Long orderId);
}
