package com.internet.shop.dao.impl;

import com.internet.shop.dao.OrderDao;
import com.internet.shop.lib.Dao;
import com.internet.shop.model.Order;
import com.internet.shop.storage.Storage;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Dao
public class OrderDaoImpl implements OrderDao {
    @Override
    public Order create(Order order) {
        Storage.addOrder(order);
        return order;
    }

    @Override
    public Optional<Order> get(Long id) {
        return Storage.orderStorage.stream()
                .filter(order -> order.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Order> getAll() {
        return Storage.orderStorage;
    }

    @Override
    public List<Order> getUserOrders(Long userId) {
        return Storage.orderStorage.stream()
                .filter(order -> order.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public Order update(Order order) {
        IntStream.range(0, Storage.orderStorage.size())
                .filter(i -> Storage.orderStorage.get(i).getId().equals(order.getId()))
                .forEach(i -> Storage.orderStorage.set(i, order));
        return order;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.orderStorage.removeIf(order -> order.getId().equals(id));
    }
}
