package com.internet.shop.dao.impl;

import com.internet.shop.dao.ShoppingCartDao;
import com.internet.shop.lib.Dao;
import com.internet.shop.model.ShoppingCart;
import com.internet.shop.storage.Storage;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Dao
public class ShoppingCartDaoImpl implements ShoppingCartDao {

    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        Storage.addShoppingCart(shoppingCart);
        return shoppingCart;
    }

    @Override
    public Optional<ShoppingCart> get(Long id) {
        return Storage.shoppingCartStorage.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<ShoppingCart> getAll() {
        return Storage.shoppingCartStorage;
    }

    @Override
    public Optional<ShoppingCart> getByUserId(Long userId) {
        return Storage.shoppingCartStorage.stream()
                .filter(c -> c.getUserId().equals(userId))
                .findFirst();
    }

    @Override
    public ShoppingCart update(ShoppingCart shoppingCart) {
        IntStream.range(0, Storage.shoppingCartStorage.size())
                .filter(i -> Storage.shoppingCartStorage.get(i).getId()
                        .equals(shoppingCart.getId()))
                .forEach(i -> Storage.shoppingCartStorage.set(i, shoppingCart));
        return shoppingCart;
    }

    @Override
    public boolean delete(ShoppingCart shoppingCart) {
        return Storage.shoppingCartStorage.removeIf(c -> c.equals(shoppingCart));
    }
}
