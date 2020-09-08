package com.internet.shop;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Product;
import com.internet.shop.model.ShoppingCart;
import com.internet.shop.model.User;
import com.internet.shop.service.OrderService;
import com.internet.shop.service.ProductService;
import com.internet.shop.service.ShoppingCartService;
import com.internet.shop.service.UserService;

public class Application {
    private static final Injector INJECTOR = Injector.getInstance("com.internet.shop");
    private static final ProductService PRODUCT_SERVICE = (ProductService) INJECTOR
            .getInstance(ProductService.class);
    private static final UserService USER_SERVICE = (UserService) INJECTOR
            .getInstance(UserService.class);
    private static final OrderService ORDER_SERVICE = (OrderService) INJECTOR
            .getInstance(OrderService.class);
    private static final ShoppingCartService SHOPPING_CART_SERVICE = (ShoppingCartService) INJECTOR
            .getInstance(ShoppingCartService.class);

    // Intentionally left empty lines to improve readability
    public static void main(String[] args) {
        // Initializing user and shopping cart for it
        User user = new User("Boris", "username", "123");
        USER_SERVICE.create(user);
        ShoppingCart shoppingCart = new ShoppingCart(user.getId());
        SHOPPING_CART_SERVICE.create(shoppingCart);

        // Initializing and adding products to productStorage
        Product productToUpdate = new Product("Something Expensive", 10000);
        Product banana = new Product("Banana", 10.3);
        Product car = new Product("Car", 17);
        PRODUCT_SERVICE.create(banana);
        PRODUCT_SERVICE.create(car);
        PRODUCT_SERVICE.create(productToUpdate);

        // Adding products to shoppingCart
        SHOPPING_CART_SERVICE.addProduct(shoppingCart, PRODUCT_SERVICE.get(1L));
        SHOPPING_CART_SERVICE.addProduct(shoppingCart, PRODUCT_SERVICE.get(2L));
        SHOPPING_CART_SERVICE.addProduct(shoppingCart, PRODUCT_SERVICE.get(3L));

        // Removing product
        SHOPPING_CART_SERVICE.deleteProduct(shoppingCart, PRODUCT_SERVICE.get(2L));

        // Show state of shopping cart after removing one product
        System.out.println(SHOPPING_CART_SERVICE.getByUserId(1L).getProducts());

        // User currently has no orders
        System.out.println(ORDER_SERVICE.getUserOrders(1L));

        ORDER_SERVICE.completeOrder(SHOPPING_CART_SERVICE.getByUserId(1L));

        // Show one order
        System.out.println(ORDER_SERVICE.getUserOrders(1L));
    }
}
