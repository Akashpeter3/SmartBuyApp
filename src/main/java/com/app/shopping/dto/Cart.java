package com.app.shopping.dto;

import com.sun.istack.NotNull;

import java.util.List;

public class Cart {

    @NotNull
    private List<Item> cartItems;




    public List<Item> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<Item> cartItems) {
        this.cartItems = cartItems;
    }

    @Override
    public String toString() {
        return "Cart{" +

                ", cartItems=" + cartItems +
                '}';
    }
}
