package com.internet.shop.model;

import java.util.List;

public class Order {
    private static Long orderCounter = 0L;
    private Long id;
    private List<Product> products;
    private Long userId;

    public Order(List<Product> products, Long userId) {
        this.id = orderCounter++;
        this.products = products;
        this.userId = userId;
    }
}
