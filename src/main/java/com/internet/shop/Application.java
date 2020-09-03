package com.internet.shop;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Product;
import com.internet.shop.service.ProductService;

public class Application {
    private static Injector injector = Injector.getInstance("com.internet.shop");

    public static void main(String[] args) {
        ProductService productService = (ProductService) injector.getInstance(ProductService.class);

        Product productToUpdate = new Product("Something Expensive", 10000);

        productService.create(new Product("Banana", 10.3));
        productService.create(new Product("Car", 17));
        productService.create(productToUpdate);

        Long idToDelete = 2L;
        System.out.println(productService.delete(idToDelete));

        productService.create(new Product("Apple", 50));

        System.out.println(productService.get(3L));

        Product newProduct = new Product("Something cheap", 0.1);
        newProduct.setId(3L);
        productService.update(newProduct);

        System.out.println(productService.getAll());

    }
}
