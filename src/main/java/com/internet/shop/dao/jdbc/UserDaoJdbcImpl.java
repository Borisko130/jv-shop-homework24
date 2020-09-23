package com.internet.shop.dao.jdbc;

import com.internet.shop.dao.UserDao;
import com.internet.shop.exceptions.DataProcessingException;
import com.internet.shop.lib.Dao;
import com.internet.shop.model.Role;
import com.internet.shop.model.User;
import com.internet.shop.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Dao
public class UserDaoJdbcImpl implements UserDao {
    @Override
    public User create(User user) {
        String query = "INSERT INTO users (user_name, user_login, user_password)"
                + " VALUES (?, ?, ?);";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create user with login "
                    + user.getLogin(), e);
        }
        return setRoles(user);
    }

    @Override
    public Optional<User> get(Long id) {
        String query = "SELECT * FROM users WHERE user_id = ? AND deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = getUserFromSet(resultSet);
                Set<Role> roles = getRolesFromDb(user);
                user.setRoles(roles);
                return Optional.of(user);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get user with id "
                    + id, e);
        }
    }

    @Override
    public List<User> getAll() {
        String query = "SELECT * FROM users WHERE deleted = false;";
        List<User> userList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery(query);
            while (resultSet.next()) {
                User user = getUserFromSet(resultSet);
                Set<Role> roles = getRolesFromDb(user);
                user.setRoles(roles);
                userList.add(user);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all users", e);
        }
        return userList;
    }

    @Override
    public User update(User user) {
        String query = "UPDATE users SET user_name = ?, user_login = ?, user_password = ? "
                + "WHERE user_id = ? AND deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setLong(4, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update user with id "
                    + user.getId(), e);
        }
        removeRoles(user);
        return setRoles(user);
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "UPDATE users SET deleted = true WHERE user_id = ?"
                + " AND deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete user with id "
                    + id, e);
        }
    }

    @Override
    public boolean delete(User user) {
        return deleteById(user.getId());
    }

    @Override
    public Optional<User> findByLogin(String login) {
        String query = "SELECT * FROM users WHERE user_login = ? AND deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = getUserFromSet(resultSet);
                return Optional.of(user);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get user with login "
                    + login, e);
        }
    }

    private User getUserFromSet(ResultSet resultSet) {
        try {
            String userName = resultSet.getString("user_name");
            String userLogin = resultSet.getString("user_login");
            String userPassword = resultSet.getString("user_password");
            Long userId = resultSet.getLong("user_id");
            User user = new User(userName, userLogin, userPassword);
            user.setId(userId);
            return user;
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to retrieve user"
                    + "from resultSet", e);
        }
    }

    private User setRoles(User user) {
        String query = "INSERT INTO users_roles (user_id, role_id)"
                + " VALUES (?, (SELECT role_id FROM roles WHERE role_name = ?));";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            for (Role role : user.getRoles()) {
                preparedStatement.setLong(1, user.getId());
                preparedStatement.setString(2, role.getRoleName().name());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to set user roles for user with id"
                    + user.getId(), e);
        }
        return user;
    }

    private Set<Role> getRolesFromDb(User user) {
        String query = "SELECT * FROM roles WHERE role_id IN " +
                "(SELECT role_id FROM users_roles WHERE user_id = ?);";
        Set<Role> userRoles = new HashSet<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long roleId = resultSet.getLong("role_id");
                String roleName = resultSet.getString("role_name");
                Role role = Role.of(roleName);
                role.setId(roleId);
                userRoles.add(role);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get user roles for user with id"
                    + user.getId(), e);
        }
        return userRoles;
    }

    private User removeRoles(User user) {
        String query = "DELETE FROM users_roles WHERE user_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to remove user roles for user with id"
                    + user.getId(), e);
        }
        return user;
    }


}
