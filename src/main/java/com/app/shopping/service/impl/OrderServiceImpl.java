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
    public Long placeOrderFromCart(Orders order) {
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
                order.setStatus("Order Placed");
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

    @Override
    public String cancelOrder(Long orderID) {
        Optional<Orders> optionalOrder = orderRepo.findById(orderID);
        if (optionalOrder.isPresent()) {
            Orders order = optionalOrder.get();
            String currentStatus = order.getStatus();
            if (currentStatus != null && !currentStatus.isEmpty()) {
                order.setStatus("Order Cancelled");
                return orderRepo.save(order).getStatus();
            }
        }
        return null;
    }

    @Override
    public String deleteOrder(Long orderId) {
        Optional<Orders> optionalOrder = orderRepo.findById(orderId);
        if (optionalOrder.isPresent()) {
            Orders order = optionalOrder.get();
            if (order != null) {
                order.setStatus("Order Deleted!");
                 orderRepo.delete(order);
                 return  "Order Deleted Successfully!";
            }
        }else {

            return "No order present for the order ID "+orderId;
        }
        return null;
    }
}
