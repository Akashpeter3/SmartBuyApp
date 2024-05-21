package com.app.shopping.controller;


import com.app.shopping.dto.Orders;
import com.app.shopping.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {


    @Autowired
    private OrderService orderService;
    @Secured("ROLE_USER")
    @PostMapping("/placeorder")
    public ResponseEntity<?> placeOrder(@RequestBody Orders orders) {
        Long orderId = orderService.placeOrder(orders);
        if (orderId != null && orderId > 0) {
            return ResponseEntity.ok(orderId);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid orderId");
        }
    }

    @Secured("ROLE_USER")
    @GetMapping("/{orderId}")
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
    @GetMapping("/getAllOrders")
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


}
