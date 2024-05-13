package com.app.shopping.service;

import com.app.shopping.dto.Orders;
import org.hibernate.criterion.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderService {

    Long placeOrder(Orders orders);
}
