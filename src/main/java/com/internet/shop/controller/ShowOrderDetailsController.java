package com.internet.shop.controller;

import com.internet.shop.lib.Injector;
import com.internet.shop.service.OrderService;

import javax.servlet.http.HttpServlet;

public class ShowOrderDetailsController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.internet.shop");
    private OrderService orderService
            = (OrderService) injector.getInstance(OrderService.class);
}
