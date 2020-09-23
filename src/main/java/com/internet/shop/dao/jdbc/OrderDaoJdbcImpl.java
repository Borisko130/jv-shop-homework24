package com.internet.shop.dao.jdbc;

import com.internet.shop.dao.OrderDao;
import com.internet.shop.model.Order;
import java.util.List;
import java.util.Optional;

public class OrderDaoJdbcImpl implements OrderDao {
    @Override
    public Order create(Order order) {
        return null;
    }

    @Override
    public Optional<Order> get(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Order> getAll() {
        return null;
    }

    @Override
    public Order update(Order order) {
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

    @Override
    public boolean delete(Order order) {
        return false;
    }

    @Override
    public List<Order> getUserOrders(Long userId) {
        return null;
    }
}
