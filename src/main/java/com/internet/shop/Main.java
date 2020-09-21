package com.internet.shop;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Product;
import com.internet.shop.service.ProductService;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("com.internet.shop");
    private static final ProductService PRODUCT_SERVICE = (ProductService) INJECTOR
            .getInstance(ProductService.class);

    public static void main(String[] args) {
        // create check
        PRODUCT_SERVICE.create(new Product("Meat", 10.0));
        PRODUCT_SERVICE.create(new Product("Bread", 10.0));
        PRODUCT_SERVICE.create(new Product("Salt", 10.0));

        //get check
        Product salt = PRODUCT_SERVICE.get(3L);
        System.out.println(salt);
        System.out.println(PRODUCT_SERVICE.getAll());

        // update check
        Product cheese = new Product("Cheese", 15.0);
        cheese.setId(2L);
        System.out.println(PRODUCT_SERVICE.update(cheese));

        // getAll check
        System.out.println(PRODUCT_SERVICE.getAll());

        // deleteById check
        PRODUCT_SERVICE.deleteById(1L);

        System.out.println(PRODUCT_SERVICE.getAll());
    }
}
