package com.internet.shop.service;

import com.internet.shop.lib.Service;
import com.internet.shop.model.Product;
import java.util.List;

@Service
public interface ProductService {
    Product create(Product product);

    Product get(Long id);

    List<Product> getAll();

    Product update(Product product);

    boolean delete(Long id);
}
