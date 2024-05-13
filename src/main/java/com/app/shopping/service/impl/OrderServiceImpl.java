package com.app.shopping.service.impl;

import com.app.shopping.dto.Orders;
import com.app.shopping.repository.OrderRepo;
import com.app.shopping.service.OrderService;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepo orderRepo;
    @Override
    public Long placeOrder(Orders order) {
      Long orderId=  orderRepo.saveAndFlush(order).getOrderId();
      return  orderId;
    }
}
