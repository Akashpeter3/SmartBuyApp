package com.app.shopping.service.impl;

import com.app.shopping.dto.Item;
import com.app.shopping.dto.Orders;
import com.app.shopping.repository.OrderRepo;
import com.app.shopping.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


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
       Orders order = orderRepo.findById(orderID).get();
       if (order != null) {
         String currentStatus=order.getStatus();
         if (currentStatus!=null&& !currentStatus.isEmpty()){
             currentStatus="Order Cancelled";
             order.setStatus(currentStatus);
          return    orderRepo.save(order).getStatus();
         }

       }
        return null;
    }
}
