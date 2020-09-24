package com.internet.shop.dao.jdbc;

import com.internet.shop.dao.ShoppingCartDao;
import com.internet.shop.exceptions.DataProcessingException;
import com.internet.shop.lib.Dao;
import com.internet.shop.model.Product;
import com.internet.shop.model.ShoppingCart;
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
public class ShoppingCartDaoJdbcImpl implements ShoppingCartDao {

    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        String query = "INSERT INTO shopping_carts (user_id) VALUES (?);";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, shoppingCart.getUserId());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                shoppingCart.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create shopping cart with id "
                    + shoppingCart.getId(), e);
        }
        return setProductsForCartInDb(shoppingCart);
    }

    @Override
    public Optional<ShoppingCart> get(Long id) {
        String query = "SELECT * FROM shopping_carts WHERE cart_id = ? AND deleted = false;";
        ShoppingCart shoppingCart = new ShoppingCart();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                shoppingCart = getShoppingCartFromSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get shopping cart with id "
                    + id, e);
        }
        List<Product> productList = getProductsListFromDb(shoppingCart);
        shoppingCart.setProducts(productList);
        return Optional.of(shoppingCart);
    }

    @Override
    public List<ShoppingCart> getAll() {
        String query = "SELECT * FROM shopping_carts WHERE deleted = false;";
        List<ShoppingCart> shoppingCartList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery(query);
            while (resultSet.next()) {
                ShoppingCart shoppingCart = getShoppingCartFromSet(resultSet);
                shoppingCartList.add(shoppingCart);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all shopping carts", e);
        }
        for (ShoppingCart shoppingCart : shoppingCartList) {
            List<Product> productList = getProductsListFromDb(shoppingCart);
            shoppingCart.setProducts(productList);
        }
        return shoppingCartList;
    }

    @Override
    public ShoppingCart update(ShoppingCart shoppingCart) {
        String query = "UPDATE shopping_carts SET user_id = ? "
                + "WHERE cart_id = ? AND deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, shoppingCart.getUserId());
            preparedStatement.setLong(2, shoppingCart.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update shopping cart with id "
                    + shoppingCart.getId(), e);
        }
        removeProductsFromCartInDb(shoppingCart);
        return setProductsForCartInDb(shoppingCart);
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "UPDATE shopping_carts SET deleted = true WHERE cart_id = ?"
                + " AND deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete shopping cart with id "
                    + id, e);
        }
    }

    @Override
    public boolean delete(ShoppingCart shoppingCart) {
        return deleteById(shoppingCart.getId());
    }

    @Override
    public Optional<ShoppingCart> getByUserId(Long userId) {
        String query = "SELECT * FROM shopping_carts WHERE user_id = ? AND deleted = false;";
        ShoppingCart shoppingCart = new ShoppingCart();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                shoppingCart = getShoppingCartFromSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get shopping cart of user with id "
                    + userId, e);
        }
        List<Product> productList = getProductsListFromDb(shoppingCart);
        shoppingCart.setProducts(productList);
        return Optional.of(shoppingCart);
    }

    private ShoppingCart getShoppingCartFromSet(ResultSet resultSet) {
        try {
            Long cartId = resultSet.getLong("cart_id");
            Long userId = resultSet.getLong("user_id");
            return new ShoppingCart(cartId, userId);
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to retrieve shopping cart"
                    + "from resultSet", e);
        }
    }

    private ShoppingCart setProductsForCartInDb(ShoppingCart shoppingCart) {
        String query = "INSERT INTO shopping_carts_products (cart_id, product_id)"
                + " VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            for (Product product : shoppingCart.getProducts()) {
                preparedStatement.setLong(1, shoppingCart.getId());
                preparedStatement.setLong(2, product.getId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't set products for shopping cart with id "
                    + shoppingCart.getId(), e);
        }
        return shoppingCart;
    }

    private List<Product> getProductsListFromDb(ShoppingCart shoppingCart) {
        String query = "SELECT * FROM products WHERE product_id IN "
                + "(SELECT product_id FROM shopping_carts_products WHERE cart_id = ? "
                + "AND deleted = false);";
        List<Product> productList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, shoppingCart.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long productId = resultSet.getLong("product_id");
                String productName = resultSet.getString("product_name");
                double productPrice = resultSet.getDouble("product_price");
                productList.add(new Product(productId, productName, productPrice));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get cart's product list from DB", e);
        }
        return productList;
    }

    private ShoppingCart removeProductsFromCartInDb(ShoppingCart shoppingCart) {
        String query = "DELETE FROM shopping_carts_products WHERE cart_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, shoppingCart.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't remove products from cart with id "
                    + shoppingCart.getId(), e);
        }
        return shoppingCart;
    }
}
