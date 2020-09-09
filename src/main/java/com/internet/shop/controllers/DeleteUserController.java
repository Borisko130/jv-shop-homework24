package com.internet.shop.controllers;

import com.internet.shop.lib.Injector;
import com.internet.shop.service.UserService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteUserController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.internet.shop");
    private final UserService userService = (UserService) injector.getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("id");
        Long id = Long.valueOf(userId);
        userService.deleteById(id);
        resp.sendRedirect(req.getContextPath() + "/users/all");
    }
}
