package com.internet.shop;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Product;
import com.internet.shop.service.ProductService;

public class Application {
    private static Injector injector = Injector.getInstance("com.internet.shop");

    public static void main(String[] args) {
        ProductService productService = (ProductService) injector.getInstance(ProductService.class);
        Product productToUpdate = new Product("Something Expensive", 10000);
        Product banana = new Product("Banana", 10.3);
        Product car = new Product("Car", 17);
        //Adding products
        productService.create(banana);
        productService.create(car);
        productService.create(productToUpdate);
        // Deleting product
        System.out.println(productService.delete(car.getId()));
        //Adding new product after deletion of existing product to check ID
        Product apple = new Product("Apple", 50);
        productService.create(apple);
        //Updating product
        Product newProduct = new Product("Something cheap", 0.1);
        newProduct.setId(productToUpdate.getId());
        productService.update(newProduct);
        //Checking get and getAll
        System.out.println(productService.get(productToUpdate.getId()));
        System.out.println(productService.getAll());
    }
}
