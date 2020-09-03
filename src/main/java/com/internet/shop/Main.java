package com.internet.shop;

import com.internet.shop.model.Product;
import com.internet.shop.storage.Storage;

public class Main {
    public static void main(String[] args) {
        Storage.addProduct(new Product("Hat", 15.0));
        Storage.addProduct(new Product("Banana", 2.0));

        System.out.println(Storage.productStorage);
    }
}
