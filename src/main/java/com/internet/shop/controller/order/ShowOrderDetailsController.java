package com.internet.shop.controller.order;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Order;
import com.internet.shop.model.Product;
import com.internet.shop.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ShowOrderDetailsController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.internet.shop");
    private OrderService orderService
            = (OrderService) injector.getInstance(OrderService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("id");
        Long id = Long.valueOf(userId);
        List<Product> products = orderService.get(id).getProducts();
        req.setAttribute("id", id);
        req.setAttribute("products", products);
        req.getRequestDispatcher("/WEB-INF/views/orders/orderDetails.jsp")
                .forward(req, resp);
    }
}
