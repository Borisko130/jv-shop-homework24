package com.internet.shop.model;

import java.util.List;

public class ShoppingCart {
    private static Long shoppingCartCounter = 0L;
    private Long id;
    private List<Product> products;
    private Long userId;

    public ShoppingCart(List<Product> products, Long userId) {
        this.id = shoppingCartCounter++;
        this.products = products;
        this.userId = userId;
    }
}
