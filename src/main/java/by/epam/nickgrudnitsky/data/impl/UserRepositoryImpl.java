package by.epam.nickgrudnitsky.data.impl;

import by.epam.nickgrudnitsky.util.JdbcConnection;
import by.epam.nickgrudnitsky.data.UserRepository;
import by.epam.nickgrudnitsky.entity.Status;
import by.epam.nickgrudnitsky.entity.User;
import by.epam.nickgrudnitsky.exception.UserRepositoryException;
import org.apache.commons.lang3.EnumUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    private static final String FIND_BY_NAME_QUERY = "SELECT * FROM users WHERE userName = '%s'";
    private static final String SAVE_USER_QUERY = "INSERT INTO users(firstName, LastName, userName," +
            "email, password, status, createdAt, updatedAt)VALUES(?,?,?,?,?,?,?,?)";
    private static final String FIND_ALL_USERS_QUERY = "SELECT userName FROM users";
    private static final String UPDATE_USER_QUERY = "UPDATE users SET firstName = ?, LastName = ?, userName = ?," +
            "email = ?, password = ?, status = ?, updatedAt = ? WHERE id = ?";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM users WHERE id = '%s'";

    private Connection connection = JdbcConnection.getConnection();

    @Override
    public User findByUsername(String userName) throws UserRepositoryException {
        String ERROR_MESSAGE = "IN UserRepositoryImpl failed to find user by userName %s";
        try {
            User user = new User();
            ResultSet resultSet = connection.createStatement().executeQuery(String.format(FIND_BY_NAME_QUERY, userName));
            if (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                user.setFirstName(resultSet.getString("firstName"));
                user.setLastName(resultSet.getString("lastName"));
                user.setUserName(userName);
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setCreated(resultSet.getDate("createdAt"));
                user.setUpdated(resultSet.getDate("updatedAt"));
                user.setStatus(EnumUtils.getEnum(Status.class, resultSet.getString("status")));
                return user;
            }
        } catch (SQLException e) {
            throw new UserRepositoryException(
                    String.format(ERROR_MESSAGE, userName), e);
        }
        throw new UserRepositoryException(
                String.format(ERROR_MESSAGE, userName));
    }

    @Override
    public User save(User user) throws UserRepositoryException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SAVE_USER_QUERY);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getUserName());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setString(6, user.getStatus().name());
            preparedStatement.setDate(7, new Date(user.getCreated().getTime()));
            preparedStatement.setDate(8, new Date(user.getCreated().getTime()));
            preparedStatement.executeUpdate();
            return user;
        } catch (SQLException e) {
            throw new UserRepositoryException(
                    String.format("IN UserRepositoryImpl failed to save user %s", user), e);
        }
    }

    @Override
    public List<User> findAll() throws UserRepositoryException {
        try {
            List<User> users = new ArrayList<>();
            ResultSet resultSet = connection.createStatement().executeQuery(FIND_ALL_USERS_QUERY);
            while (resultSet.next()) {
                users.add(findByUsername(resultSet.getString(1)));
            }
            return users;
        } catch (SQLException e) {
            throw new UserRepositoryException("IN UserRepositoryImpl failed to find all users.", e);
        }
    }

    @Override
    public User update(User user) throws UserRepositoryException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_QUERY);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getUserName());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setString(6, user.getStatus().name());
            preparedStatement.setDate(7, new Date(user.getCreated().getTime()));
            preparedStatement.setInt(8, user.getId());
            preparedStatement.executeUpdate();
            return user;
        } catch (SQLException e) {
            throw new UserRepositoryException(
                    String.format("IN UserRepositoryImpl failed to update user %s", user), e);
        }
    }

    @Override
    public User findById(Integer id) throws UserRepositoryException {
        String ERROR_MESSAGE = "IN UserRepositoryImpl failed ti find user by id %d";
        try {
            User user = new User();
            ResultSet resultSet = connection.createStatement().executeQuery(String.format(FIND_BY_ID_QUERY, id));
            if (resultSet.next()) {
                user.setId(id);
                user.setFirstName(resultSet.getString("firstName"));
                user.setLastName(resultSet.getString("lastName"));
                user.setUserName(resultSet.getString("userName"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setCreated(resultSet.getDate("createdAt"));
                user.setUpdated(resultSet.getDate("updatedAt"));
                user.setStatus(EnumUtils.getEnum(Status.class, resultSet.getString("status")));
                return user;
            }
        } catch (SQLException e) {
            throw new UserRepositoryException(
                    String.format(ERROR_MESSAGE, id), e);
        }
        throw new UserRepositoryException(
                String.format(ERROR_MESSAGE, id));
    }
}
