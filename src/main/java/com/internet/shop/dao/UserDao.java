package com.internet.shop.dao;

import com.internet.shop.model.User;
import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<User> get(Long id);

    List<User> getAll();

    void save(User user);

    void update(User user, String[] params);

    void delete(User user);
}
