package com.app.shopping.controller;


import com.app.shopping.dto.Cart;
import com.app.shopping.dto.Item;
import com.app.shopping.dto.Orders;
import com.app.shopping.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {


    @Secured("ROLE_USER")
    @PostMapping("/orders/placeOrderByItem")
    public ResponseEntity<?> placeOrderByItem(@RequestBody Item item) {
        Orders order = new Orders();
        mapItemListWithOrder(order, item);
       
        order.setCustomerId(item.getOrder().getCustomerId());
        Long orderId = orderService.placeOrder(order);
        if (orderId != null && orderId > 0) {
            return ResponseEntity.ok(orderId);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid orderId");
        }
    }

    private void mapItemListWithOrder(Orders order, Item item) {
    }

    @Autowired
    private OrderService orderService;
    @Secured("ROLE_USER")
    @PostMapping("/orders/placeOrderFromCart")
    public ResponseEntity<?> placeOrderFromCart(@RequestBody Cart cart) {
        Orders order = new Orders();
        Orders orders =  placeOrder(cart, order);
        Long orderId = orderService.placeOrder(orders);
        if (orderId != null && orderId > 0) {
            return ResponseEntity.ok(orderId);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid orderId");
        }
    }


    private Orders placeOrder(Cart cart, Orders order) {
        order.setOrderItems(cart.getCartItems());
        order.setCustomerId(cart.getCustomerId());
        return order;
    }


    @Secured("ROLE_USER")
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<?> getOrderByID(@PathVariable Long orderId) {
        try {
            Orders order = orderService.getOrderByID(orderId);
            if (order != null) {
                return new ResponseEntity<>(order, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Order not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while retrieving the order", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Secured("ROLE_USER")
    @GetMapping("/orders/getAllOrders")
    public ResponseEntity<?> getAllOrders() {
        try {
            List<Orders> orderList = orderService.getAllOrders();
            if (orderList != null) {
                return new ResponseEntity<>(orderList, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Order not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while retrieving the order", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Secured("ROLE_USER")
    @PutMapping("/orders/cancel/{orderId}")
    public ResponseEntity<?> cancelOrder(@PathVariable Long orderId) {
      String status = orderService.cancelOrder(orderId);
      if (status != null&&!status.isEmpty()){
          return  new ResponseEntity<>(status,HttpStatus.OK);
      }else {
          return new ResponseEntity<>("Order not found for the OrderID "+orderId, HttpStatus.NOT_FOUND);
      }
    }


    @Secured("ROLE_USER")
    @DeleteMapping("/orders/delete/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long orderId) {
        String status = orderService.deleteOrder(orderId);
        if (status != null&&!status.isEmpty()){
            return  new ResponseEntity<>(status,HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Invalid OrderID "+orderId, HttpStatus.NOT_FOUND);
        }
    }


}
