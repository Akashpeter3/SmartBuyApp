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

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Secured("ROLE_USER")
    @PostMapping("/orders/placeOrderByItem/{customerID}")
    public ResponseEntity<?> placeOrderByItem(@RequestBody Item item, @PathVariable Long customerID) {
        if (customerID > 0) {
            Orders order = new Orders();
            Orders orders = mapItemListWithOrder(order, item, customerID);
            Long orderId = orderService.placeOrder(orders);
            if (orderId != null && orderId > 0) {
                return ResponseEntity.ok(orderId);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid orderId");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No customer ID found for this order");
        }
    }

    private Orders mapItemListWithOrder(Orders order, Item item, Long customerID) {
        List<Item> itemList = new ArrayList<>();
        itemList.add(item);
        order.setOrderItems(itemList);
        order.setStatus("Order Placed");
        order.setCustomerId(customerID);
        return order;
    }

    @Secured("ROLE_USER")
    @PostMapping("/orders/placeOrderFromCart/{customerID}")
    public ResponseEntity<?> placeOrderFromCart(@RequestBody Cart cart, @PathVariable Long customerID) {
        if (customerID != null && customerID > 0) {
            Orders order = new Orders();
            Orders orders = placeOrder(cart, order, customerID);
            Long orderId = orderService.placeOrder(orders);
            if (orderId != null && orderId > 0) {
                return ResponseEntity.ok(orderId);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid orderId");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No customer ID found for this order");
        }
    }

    private Orders placeOrder(Cart cart, Orders order, Long customerID) {
        order.setOrderItems(cart.getCartItems());
        order.setCustomerId(customerID);
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
            return new ResponseEntity<>("An error occurred while retrieving the orders", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Secured("ROLE_USER")
    @PutMapping("/orders/cancel/{orderId}")
    public ResponseEntity<?> cancelOrder(@PathVariable Long orderId) {
        String status = orderService.cancelOrder(orderId);
        if (status != null && !status.isEmpty()) {
            return new ResponseEntity<>(status, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Order not found for the OrderID " + orderId, HttpStatus.NOT_FOUND);
        }
    }

    @Secured("ROLE_USER")
    @DeleteMapping("/orders/delete/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long orderId) {
        String status = orderService.deleteOrder(orderId);
        if (status != null && !status.isEmpty()) {
            return new ResponseEntity<>(status, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid OrderID " + orderId, HttpStatus.NOT_FOUND);
        }
    }
}
