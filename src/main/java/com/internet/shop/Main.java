package com.internet.shop;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Product;
import com.internet.shop.model.User;
import com.internet.shop.service.ProductService;
import com.internet.shop.service.UserService;

public class Main {
    private static final Injector injector = Injector.getInstance("com.internet.shop");
    private static final ProductService productService = (ProductService) injector
            .getInstance(ProductService.class);
    private static final UserService userService = (UserService) injector
            .getInstance(UserService.class);

    public static void main(String[] args) {
        // create check
        /*productService.create(new Product("Meat", 10.0));
        productService.create(new Product("Bread", 10.0));
        productService.create(new Product("Salt", 10.0));

        //get check
        Product salt = productService.get(3L);
        System.out.println(salt);
        System.out.println(productService.getAll());

        // update check
        Product cheese = new Product("Cheese", 15.0);
        cheese.setId(2L);
        System.out.println(productService.update(cheese));

        // getAll check
        System.out.println(productService.getAll());

        // deleteById check
        productService.deleteById(1L);

        System.out.println(productService.getAll());*/

        System.out.println("\nUSERS\n");
        /*userService.create(new User("Boris", "u1", "1"));
        userService.create(new User("Alice", "u2", "2"));
        userService.create(new User("Bob", "bob", "3"));*/

        System.out.println(userService.getAll());
        System.out.println(userService.get(2L));
        System.out.println(userService.findByLogin("Bob"));
    }
}
