package com.internet.shop.controller;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Product;
import com.internet.shop.service.ShoppingCartService;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowCartController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.internet.shop");
    private static final Long USER_ID = 1L;
    private ShoppingCartService shoppingCartService
            = (ShoppingCartService) injector.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Product> cart = null;
        try {
            cart = shoppingCartService.getByUserId(USER_ID).getProducts();
        } catch (NoSuchElementException e) {
            req.setAttribute("message", "Your cart is empty");
        }
        req.setAttribute("cart", cart);
        req.getRequestDispatcher("/WEB-INF/views/shopping-cart/products/cart.jsp")
                .forward(req, resp);
    }
}
