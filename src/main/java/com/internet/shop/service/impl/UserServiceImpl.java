package com.internet.shop.service.impl;

import com.internet.shop.dao.UserDao;
import com.internet.shop.lib.Inject;
import com.internet.shop.lib.Service;
import com.internet.shop.model.User;
import com.internet.shop.service.UserService;
import com.internet.shop.util.HashUtil;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Inject
    private UserDao userDao;

    @Override
    public User create(User user) {
        return userDao.create(setPassAndSalt(user));
    }

    @Override
    public User get(Long id) {
        return userDao.get(id).get();
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public User update(User user) {
        return userDao.update(setPassAndSalt(user));
    }

    @Override
    public boolean deleteById(Long id) {
        return userDao.deleteById(id);
    }

    @Override
    public boolean delete(User user) {
        return userDao.delete(user);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return userDao.findByLogin(login);
    }

    private User setPassAndSalt(User user) {
        byte[] salt = HashUtil.getSalt();
        String pass = HashUtil.hashPassword(user.getPassword(),salt);
        user.setSalt(salt);
        user.setPassword(pass);
        return user;
    }
}
