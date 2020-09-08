package com.internet.shop.dao;

import java.util.List;
import java.util.Optional;

// TODO Refactor Impls using this
public interface GenericDao<T> {
    T create(T t);

    Optional<T> get(Long id);

    List<T> getAll();

    T update(T t);

    boolean deleteById(Long id);

    boolean delete(T t);
}
