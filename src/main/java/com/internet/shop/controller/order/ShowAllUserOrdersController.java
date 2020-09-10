package com.internet.shop.controller.order;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Order;
import com.internet.shop.service.OrderService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ShowAllUserOrdersController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.internet.shop");
    private static final Long USER_ID = 1L;
    private OrderService orderService
            = (OrderService) injector.getInstance(OrderService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Order> orders = orderService.getUserOrders(USER_ID);
        req.setAttribute("orders", orders);
        req.getRequestDispatcher("/WEB-INF/views/orders/allUserOrders.jsp")
                .forward(req, resp);
    }
}
