package com.internet.shop.dao;

import com.internet.shop.lib.Dao;
import com.internet.shop.model.Product;
import com.internet.shop.storage.Storage;
import java.util.List;
import java.util.Optional;

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
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Product> getAll() {
        return Storage.productStorage;
    }

    @Override
    public Product update(Product product) {
        for (Product prod : Storage.productStorage) {
            if (prod.getId().equals(product.getId())) {
                prod.setName(product.getName());
                prod.setPrice(product.getPrice());
            }
        }
        return product;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.productStorage.removeIf(product -> product.getId().equals(id));
    }
}
