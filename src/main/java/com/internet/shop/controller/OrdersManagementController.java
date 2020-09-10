package com.internet.shop.controller;

import com.internet.shop.lib.Injector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// TODO implement
public class OrdersManagementController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.internet.shop");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        super.doGet(req, resp);
    }
}
