package com.internet.shop.dao;

import com.internet.shop.lib.Dao;
import com.internet.shop.model.User;
import com.internet.shop.storage.Storage;

import java.util.List;
import java.util.Optional;

@Dao
public class UserDaoImpl implements UserDao{
    @Override
    public Optional<User> get(Long id) {
        return Storage.userStorage.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<User> getAll() {
        return Storage.userStorage;
    }

    @Override
    public void save(User user) {
        Storage.userStorage.add(user);
    }

    // TODO implement update mehtod for userDao
    @Override
    public void update(User user, String[] params) {

    }

    @Override
    public void delete(User user) {
        Storage.userStorage.remove(user);
    }
}
