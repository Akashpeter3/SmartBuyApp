package com.app.shopping.dto;

import com.sun.istack.NotNull;

import java.util.List;

public class Cart {
    @NotNull
    private Long customerId;
    @NotNull
    private List<Item> cartItems;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<Item> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<Item> cartItems) {
        this.cartItems = cartItems;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "customerId=" + customerId +
                ", cartItems=" + cartItems +
                '}';
    }
}
