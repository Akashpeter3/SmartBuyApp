package com.app.shopping.service;

import com.app.shopping.dto.Orders;
import org.hibernate.criterion.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderService {

    Long placeOrder(Orders orders);

    Orders getOrderByID(Long orderId);


    List<Orders> getAllOrders();
}
