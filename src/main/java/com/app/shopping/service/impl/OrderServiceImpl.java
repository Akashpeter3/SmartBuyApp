package com.app.shopping.service.impl;

import com.app.shopping.dto.Item;
import com.app.shopping.dto.Orders;
import com.app.shopping.repository.OrderRepo;
import com.app.shopping.service.OrderService;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepo orderRepo;
    @Override
    public Long placeOrder(Orders order) {
        double price = 0;
        double newPrice=0;
        double totalPrice = 0;
        if (order!=null) {
           for(Item item : order.getOrderItems()) {
            price = item.getPrice();
            if (price!=0) {
                 newPrice= item.getQuantity()*price;
                 totalPrice+=newPrice;
            }else {

                break;
            }

           }
            if (totalPrice!=0) {
                order.setTotalPrice(totalPrice);
            }else {
                System.out.println("Price should not be zero");
                return  null;
            }


        }
        return orderRepo.saveAndFlush(order).getOrderId();
    }

    @Override
    public Orders getOrderByID(Long orderId) {
        return orderRepo.findById(orderId).get();
    }

    @Override
    public List<Orders> getAllOrders() {
        return orderRepo.findAll();
    }
}
