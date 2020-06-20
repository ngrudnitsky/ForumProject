package by.epam.nickgrudnitsky.data.impl;

import by.epam.nickgrudnitsky.util.JdbcConnection;
import by.epam.nickgrudnitsky.data.UserRepository;
import by.epam.nickgrudnitsky.entity.Status;
import by.epam.nickgrudnitsky.entity.User;
import by.epam.nickgrudnitsky.exception.UserRepositoryException;
import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    private final Logger log = LoggerFactory.getLogger(UserRepositoryImpl.class);
    private final Connection connection = JdbcConnection.getConnection();

    @Override
    public User findByUsername(String userName) throws UserRepositoryException {
        String errorMessage = String.format(
                "IN UserRepositoryImpl.findByUsername failed to find user by userName %s", userName);
        try {
            String findByNameQuery = String.format(
                    "SELECT * FROM users WHERE userName = '%s'", userName);
            User user = new User();
            ResultSet resultSet = connection.createStatement().executeQuery(findByNameQuery);
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
                //todo log.info for all repos
                return user;
            }
        } catch (SQLException e) {
            log.error(errorMessage);
            throw new UserRepositoryException(errorMessage, e);
        }
        log.error(errorMessage);
        throw new UserRepositoryException(errorMessage);
    }

    @Override
    public User create(User user) throws UserRepositoryException {
        try {
            String saveUserQuery = "INSERT INTO users(firstName, LastName, userName," +
                    "email, password, status, createdAt, updatedAt)VALUES(?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(saveUserQuery);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getUserName());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setString(6, user.getStatus().name());
            preparedStatement.setDate(7, new Date(user.getCreated().getTime()));
            preparedStatement.setDate(8, new Date(user.getCreated().getTime()));
            preparedStatement.executeUpdate();
            //todo findUsersId
            user = findByUsername(user.getUserName());
            return user;
        } catch (SQLException e) {
            String errorMessage = String.format(
                    "IN UserRepositoryImpl.create failed to create new user %s", user.getUserName());
            log.error(errorMessage);
            throw new UserRepositoryException(errorMessage, e);
        }
    }

    //todo findFromTo
    @Override
    public List<User> findAll() throws UserRepositoryException {
        try {
            String findAllUsersQuery = "SELECT userName FROM users";
            List<User> users = new ArrayList<>();
            ResultSet resultSet = connection.createStatement().executeQuery(findAllUsersQuery);
            while (resultSet.next()) {
                users.add(findByUsername(resultSet.getString(1)));
            }
            return users;
        } catch (SQLException e) {
            String errorMessage = "IN UserRepositoryImpl.findAll failed to find all users.";
            log.error(errorMessage);
            throw new UserRepositoryException(errorMessage, e);
        }
    }

    @Override
    public User update(User user) throws UserRepositoryException {
        try {
            String updateUserQuery = "UPDATE users SET firstName = ?, LastName = ?, userName = ?," +
                    "email = ?, password = ?, status = ?, updatedAt = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateUserQuery);
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
            String errorMessage = String.format(
                    "IN UserRepositoryImpl.update failed to update user %s", user.getUserName());
            log.error(errorMessage);
            throw new UserRepositoryException(errorMessage, e);
        }
    }

    @Override
    public User deleteById(Integer id) throws UserRepositoryException {
        try {
            String updateUserQuery = "UPDATE users SET status = ?, updatedAt = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateUserQuery);
            preparedStatement.setString(1, Status.DELETED.name());
            preparedStatement.setDate(2, new Date(System.currentTimeMillis()));
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
            return findById(id);
        } catch (SQLException e) {
            String errorMessage = String.format(
                    "IN UserRepositoryImpl.deleteById failed to delete user with id %s", id);
            log.error(errorMessage);
            throw new UserRepositoryException(errorMessage, e);
        }
    }

    @Override
    public User findById(Integer id) throws UserRepositoryException {
        String errorMessage = String.format("IN UserRepositoryImpl.findById failed to find user by id %s", id);
        try {
            String findByIdQuery = String.format("SELECT * FROM users WHERE id = '%s'", id);
            ResultSet resultSet = connection.createStatement().executeQuery(findByIdQuery);
            if (resultSet.next()) {
                User user = new User();
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
            log.error(errorMessage);
            throw new UserRepositoryException(errorMessage, e);
        }
        log.error(errorMessage);
        throw new UserRepositoryException(errorMessage);
    }
}
