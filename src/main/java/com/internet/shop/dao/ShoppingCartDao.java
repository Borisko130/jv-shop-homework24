package com.internet.shop.dao;

import com.internet.shop.model.ShoppingCart;
import java.util.List;
import java.util.Optional;

public interface ShoppingCartDao extends GenericDao<ShoppingCart> {
    Optional<ShoppingCart> getByUserId(Long userId);
}
