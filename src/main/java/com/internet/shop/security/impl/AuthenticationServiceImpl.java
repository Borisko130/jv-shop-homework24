package com.internet.shop.security.impl;

import com.internet.shop.exceptions.AuthenticationException;
import com.internet.shop.lib.Inject;
import com.internet.shop.lib.Service;
import com.internet.shop.model.User;
import com.internet.shop.security.AuthenticationService;
import com.internet.shop.service.UserService;
import com.internet.shop.util.HashUtil;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String login, String password) throws AuthenticationException {
        Optional<User> userFromDb = userService.findByLogin(login);
        if (userFromDb.isPresent() && checkPassword(userFromDb.get(), password)) {
            return userFromDb.get();
        }
        throw new AuthenticationException("Incorrect username or password");
    }

    private boolean checkPassword(User user, String inputPassword) {
        String generatedPassword = HashUtil.hashPassword(inputPassword,user.getSalt());
        return generatedPassword.equals(user.getPassword());
    }
}
