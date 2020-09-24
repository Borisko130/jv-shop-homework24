package com.internet.shop.dao.jdbc;

import com.internet.shop.dao.OrderDao;
import com.internet.shop.exceptions.DataProcessingException;
import com.internet.shop.lib.Dao;
import com.internet.shop.model.Order;
import com.internet.shop.model.Product;
import com.internet.shop.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class OrderDaoJdbcImpl implements OrderDao {
    @Override
    public Order create(Order order) {
        String query = "INSERT INTO orders (user_id) VALUES (?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, order.getUserId());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                order.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create order with id "
                    + order.getId(), e);
        }
        return setProductsForOrderInDb(order);
    }

    @Override
    public Optional<Order> get(Long id) {
        String query = "SELECT * FROM orders WHERE order_id = ? AND deleted = false";
        Order order = new Order();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                order = getOrderFromSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get order with id "
                    + id, e);
        }
        List<Product> productList = getProductsListFromDb(order);
        order.setProducts(productList);
        return Optional.of(order);
    }

    @Override
    public List<Order> getAll() {
        String query = "SELECT * FROM orders WHERE deleted = false;";
        List<Order> orderList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery(query);
            while (resultSet.next()) {
                Order order = getOrderFromSet(resultSet);
                orderList.add(order);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all shopping carts", e);
        }
        for (Order order : orderList) {
            List<Product> productList = getProductsListFromDb(order);
            order.setProducts(productList);
        }
        return orderList;
    }

    @Override
    public Order update(Order order) {
        String query = "UPDATE orders SET user_id = ? "
                + "WHERE order_id = ? AND deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, order.getUserId());
            preparedStatement.setLong(2, order.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update order with id "
                    + order.getId(), e);
        }
        removeProductsFromOrderInDb(order);
        return setProductsForOrderInDb(order);
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "UPDATE orders SET deleted = true WHERE order_id = ?"
                + " AND deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete order with id "
                    + id, e);
        }
    }

    @Override
    public boolean delete(Order order) {
        return deleteById(order.getId());
    }

    @Override
    public List<Order> getUserOrders(Long userId) {
        String query = "SELECT * FROM orders WHERE user_id = ? AND deleted = false;";
        List<Order> orderList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long orderId = resultSet.getLong("order_id");
                orderList.add(new Order(orderId, userId));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get orders of user with id" + userId, e);
        }
        for (Order order : orderList) {
            List<Product> productList = getProductsListFromDb(order);
            order.setProducts(productList);
        }
        return orderList;
    }

    private Order getOrderFromSet(ResultSet resultSet) {
        try {
            Long orderId = resultSet.getLong("order_id");
            Long userId = resultSet.getLong("user_id");
            return new Order(orderId, userId);
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to retrieve order"
                    + "from resultSet", e);
        }
    }

    private Order setProductsForOrderInDb(Order order) {
        String query = "INSERT INTO orders_products (order_id, product_id)"
                + " VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            for (Product product : order.getProducts()) {
                preparedStatement.setLong(1, order.getId());
                preparedStatement.setLong(2, product.getId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't set products for order with id "
                    + order.getId(), e);
        }
        return order;
    }

    private List<Product> getProductsListFromDb(Order order) {
        String query = "SELECT * FROM products WHERE product_id IN "
                + "(SELECT product_id FROM orders_products WHERE order_id = ? "
                + "AND deleted = false);";
        List<Product> productList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, order.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long productId = resultSet.getLong("product_id");
                String productName = resultSet.getString("product_name");
                double productPrice = resultSet.getDouble("product_price");
                productList.add(new Product(productId, productName, productPrice));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get order's product list from DB", e);
        }
        return productList;
    }

    private Order removeProductsFromOrderInDb(Order order) {
        String query = "DELETE FROM orders_products WHERE order_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, order.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't remove products from order with id "
                    + order.getId(), e);
        }
        return order;
    }
}
