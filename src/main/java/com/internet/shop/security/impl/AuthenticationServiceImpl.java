package com.internet.shop.security.impl;

import com.internet.shop.exceptions.AuthenticationException;
import com.internet.shop.lib.Inject;
import com.internet.shop.lib.Service;
import com.internet.shop.model.User;
import com.internet.shop.security.AuthenticationService;
import com.internet.shop.service.UserService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final String ERROR_MESSAGE = "Incorrect username or password";

    @Inject
    private UserService userService;

    @Override
    public User login(String login, String password) throws AuthenticationException {
        User userFromDb = userService.findByLogin(login).orElseThrow(() ->
                new AuthenticationException(ERROR_MESSAGE));
        if (userFromDb.getPassword().equals(password)) {
            return userFromDb;
        }
        throw new AuthenticationException(ERROR_MESSAGE);
    }
}
