package com.internet.shop;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Product;
import com.internet.shop.model.Role;
import com.internet.shop.model.ShoppingCart;
import com.internet.shop.model.User;
import com.internet.shop.service.ProductService;
import com.internet.shop.service.ShoppingCartService;
import com.internet.shop.service.UserService;

import java.util.Set;

public class Main {
    private static final Injector injector = Injector.getInstance("com.internet.shop");
    private static final ProductService productService = (ProductService) injector
            .getInstance(ProductService.class);
    private static final UserService userService = (UserService) injector
            .getInstance(UserService.class);
    private static final ShoppingCartService shoppingCartService = (ShoppingCartService) injector
            .getInstance(ShoppingCartService.class);

    public static void main(String[] args) {
        /*productService.create(new Product("Meat", 10.0));
        productService.create(new Product("Bread", 10.0));
        productService.create(new Product("Salt", 10.0));
        Product salt = productService.get(3L);
        System.out.println(salt);
        System.out.println(productService.getAll());
        Product cheese = new Product("Cheese", 15.0);
        cheese.setId(2L);
        System.out.println(productService.update(cheese));
        System.out.println(productService.getAll());
        productService.deleteById(1L);
        System.out.println(productService.getAll());*/

        /*System.out.println("\nUSERS\n");
        userService.create(new User("Boris", "u1", "1", Set.of(Role.of("ADMIN"))));
        userService.create(new User("Alice", "u2", "2", Set.of(Role.of("USER"))));
        userService.create(new User("Bob", "bob", "3", Set.of(Role.of("USER"))));

        System.out.println(userService.getAll());
        System.out.println(userService.get(2L));
        System.out.println(userService.findByLogin("Bob"));
        userService.deleteById(8L);
        User john = new User("John", "john", "j",
                Set.of(Role.of("ADMIN"), Role.of("USER")));
        john.setId(7L);
        userService.update(john);
        System.out.println(userService.getAll());*/

        System.out.println("\nSHOPPING CARTS\n");
        /*shoppingCartService.create(new ShoppingCart(1L));
        shoppingCartService.create(new ShoppingCart(3L));
        shoppingCartService.create(new ShoppingCart(1L));
        System.out.println(shoppingCartService.get(2L));
        ShoppingCart shoppingCart = new ShoppingCart(1L);
        shoppingCart.setId(2L);
        shoppingCartService.update(shoppingCart);
        System.out.println(shoppingCartService.getAll());
        */
        shoppingCartService.addProduct(shoppingCartService.get(1L), productService.get(3L));
    }
}
