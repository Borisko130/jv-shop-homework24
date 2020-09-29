package com.internet.shop.controller.util;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Product;
import com.internet.shop.model.Role;
import com.internet.shop.model.ShoppingCart;
import com.internet.shop.model.User;
import com.internet.shop.service.ProductService;
import com.internet.shop.service.ShoppingCartService;
import com.internet.shop.service.UserService;
import com.internet.shop.util.HashUtil;
import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InjectDataController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.internet.shop");
    private UserService userService = (UserService) injector.getInstance(UserService.class);
    private ProductService productService
            = (ProductService) injector.getInstance(ProductService.class);
    private ShoppingCartService shoppingCartService
            = (ShoppingCartService) injector.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user1 = new User("Boris", "u1", "1", HashUtil.getSalt(), Set.of(Role.of("ADMIN")));
        User user2 = new User("NotBoris", "u2", "1", HashUtil.getSalt(), Set.of(Role.of("USER")));
        userService.create(user1);
        userService.create(user2);
        shoppingCartService.create(new ShoppingCart(user1.getId()));
        shoppingCartService.create(new ShoppingCart(user2.getId()));
        Product bread = new Product("Bread", 10);
        Product meat = new Product("Meat", 133);
        productService.create(bread);
        productService.create(meat);

        req.getRequestDispatcher("/WEB-INF/views/inject-data.jsp").forward(req, resp);
    }
}
