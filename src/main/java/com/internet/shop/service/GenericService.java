package com.internet.shop.service;

import java.util.List;

public interface GenericService<T> {
    T create(T t);

    T get(Long id);

    List<T> getAll();

    T update(T t);

    boolean deleteById(Long id);

    boolean delete(T t);
}
