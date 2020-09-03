package com.internet.shop.storage;

import com.internet.shop.model.Order;
import com.internet.shop.model.Product;
import com.internet.shop.model.ShoppingCart;
import com.internet.shop.model.User;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    public static final List<User> userStorage = new ArrayList<>();
    public static final List<Product> productStorage = new ArrayList<>();
    public static final List<Order> orderStorage = new ArrayList<>();
    public static final List<ShoppingCart> shoppingCartStorage = new ArrayList<>();
    private static long productCounter = 1L;
    private static long userCounter = 1L;
    private static long shoppingCartCounter = 1L;
    private static long orderCounter = 1L;

    public static void addProduct(Product product) {
        product.setId(productCounter++);
        productStorage.add(product);
    }

    public static void addUser(User user) {
        user.setId(userCounter++);
        userStorage.add(user);
    }

    public static void addShoppingCart(ShoppingCart shoppingCart) {
        shoppingCart.setId(shoppingCartCounter++);
        shoppingCartStorage.add(shoppingCart);
    }

    public static void addOrder(Order order) {
        order.setId(orderCounter++);
        orderStorage.add(order);
    }
}
