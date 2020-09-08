package com.internet.shop.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T, K> {
    T create(T t);

    Optional<T> get(K id);

    List<T> getAll();

    T update(T t);

    boolean deleteById(K id);

    boolean delete(T t);
}
