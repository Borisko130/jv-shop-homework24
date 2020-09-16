package com.internet.shop.controller.user;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Role;
import com.internet.shop.model.ShoppingCart;
import com.internet.shop.model.User;
import com.internet.shop.service.ShoppingCartService;
import com.internet.shop.service.UserService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddUserController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.internet.shop");
    private UserService userService = (UserService) injector.getInstance(UserService.class);
    private ShoppingCartService shoppingCartService
            = (ShoppingCartService) injector.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/users/addUser.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String pass = req.getParameter("pass");
        String passRep = req.getParameter("pass-repeat");

        if (name.length() == 0
                || login.length() == 0
                || pass.length() == 0) {
            req.setAttribute("message", "Name, login or password field is empty");
            req.getRequestDispatcher("/WEB-INF/views/users/addUser.jsp").forward(req, resp);
        } else if (pass.equals(passRep)) {
            User user = new User(name, login, pass);
            userService.create(user);
            // If this user has id 1, then it will be ADMIN
            if (user.getId() == 1L) {
                user.setRoles(List.of(Role.of("ADMIN")));
            } else {
                user.setRoles(List.of(Role.of("USER")));
            }
            shoppingCartService.create(new ShoppingCart(user.getId()));
            resp.sendRedirect(req.getContextPath() + "/");
        } else {
            req.setAttribute("message", "Your password and confirmation password are not same");
            req.getRequestDispatcher("/WEB-INF/views/users/addUser.jsp").forward(req, resp);
        }
    }
}
