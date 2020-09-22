package com.internet.shop;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Product;
import com.internet.shop.service.ProductService;

public class Main {
    private static final Injector injector = Injector.getInstance("com.internet.shop");
    private static final ProductService product_service = (ProductService) injector
            .getInstance(ProductService.class);

    public static void main(String[] args) {
        // create check
        product_service.create(new Product("Meat", 10.0));
        product_service.create(new Product("Bread", 10.0));
        product_service.create(new Product("Salt", 10.0));

        //get check
        Product salt = product_service.get(3L);
        System.out.println(salt);
        System.out.println(product_service.getAll());

        // update check
        Product cheese = new Product("Cheese", 15.0);
        cheese.setId(2L);
        System.out.println(product_service.update(cheese));

        // getAll check
        System.out.println(product_service.getAll());

        // deleteById check
        product_service.deleteById(1L);

        System.out.println(product_service.getAll());
    }
}
