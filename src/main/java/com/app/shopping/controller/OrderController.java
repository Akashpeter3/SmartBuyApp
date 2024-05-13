package com.app.shopping.controller;


import com.app.shopping.dto.Orders;
import com.app.shopping.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {


    @Autowired
    private OrderService orderService;
    @PostMapping("/placeorder")
    ResponseEntity<?>placeorder(@RequestBody Orders orders ){
      Long orderId= orderService.placeOrder(orders);
        if (orderId == null || orderId == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid orderId");
        } else {
            return ResponseEntity.ok(orderId);
        }
    }

}
