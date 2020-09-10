package com.internet.shop.controller.cart;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.ShoppingCart;
import com.internet.shop.service.ShoppingCartService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteProductFromCartController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.internet.shop");
    private static final Long USER_ID = 1L;
    private ShoppingCartService shoppingCartService
            = (ShoppingCartService) injector.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String productId = req.getParameter("id");
        int id = Integer.parseInt(productId);
        ShoppingCart shoppingCart = shoppingCartService.getByUserId(USER_ID);
        shoppingCartService.deleteProduct(shoppingCart,
                shoppingCart.getProducts().get(id));
        resp.sendRedirect(req.getContextPath() + "/shopping-cart/products/");
    }
}
