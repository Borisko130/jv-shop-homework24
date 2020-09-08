package com.internet.shop.dao.impl;

import com.internet.shop.dao.ProductDao;
import com.internet.shop.lib.Dao;
import com.internet.shop.model.Product;
import com.internet.shop.storage.Storage;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Dao
public class ProductDaoImpl implements ProductDao {
    @Override
    public Product create(Product product) {
        Storage.addProduct(product);
        return product;
    }

    @Override
    public Optional<Product> get(Long id) {
        return Storage.productStorage.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Product> getAll() {
        return Storage.productStorage;
    }

    @Override
    public Product update(Product product) {
        IntStream.range(0, Storage.productStorage.size())
                .filter(i -> Storage.productStorage.get(i).getId().equals(product.getId()))
                .forEach(i -> Storage.productStorage.set(i, product));
        return product;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.productStorage.removeIf(p -> p.getId().equals(id));
    }
}
