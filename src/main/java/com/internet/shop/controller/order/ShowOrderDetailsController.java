package com.internet.shop.controller.order;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Product;
import com.internet.shop.service.OrderService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowOrderDetailsController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.internet.shop");
    private OrderService orderService
            = (OrderService) injector.getInstance(OrderService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String orderId = req.getParameter("id");
        Long id = Long.valueOf(orderId);
        List<Product> products = orderService.get(id).getProducts();
        req.setAttribute("id", id);
        req.setAttribute("products", products);
        req.getRequestDispatcher("/WEB-INF/views/orders/orderDetails.jsp")
                .forward(req, resp);
    }
}
