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
    private Connection connection = JdbcConnection.getConnection();

    @Override
    public User findByUsername(String userName) throws SQLException, UserRepositoryException {
        User user;
        ResultSet resultSet =
                connection.createStatement().executeQuery(String.format("SELECT * FROM users WHERE userName = '%s'", userName));
        if (resultSet.next()) {
            user = new User();
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
        throw new UserRepositoryException(String.format("IN findByUsername there is no user found by userName %s", userName));
    }

    @Override
    public User save(User user) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users(firstName, LastName, userName," +
                "email, password, status, createdAt, updatedAt)VALUES(?,?,?,?,?,?,?,?)");
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
    }

    @Override
    public List<User> findAll() throws SQLException, UserRepositoryException {
        List<User> users = new ArrayList<>();
        ResultSet resultSet =
                connection.createStatement().executeQuery("SELECT userName FROM users");
        while (resultSet.next()) {
            users.add(findByUsername(resultSet.getString(1)));
        }
        return users;
    }

    @Override
    public User update(User user) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE users SET firstName = ?, LastName = ?, userName = ?," +
                "email = ?, password = ?, status = ?, updatedAt = ? WHERE id = ?");
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
    }

    @Override
    public User findById(Integer id) throws SQLException, UserRepositoryException {
        User user;
        ResultSet resultSet =
                connection.createStatement().executeQuery(String.format("SELECT * FROM users WHERE id = '%s'", id));
        if (resultSet.next()) {
            user = new User();
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
        throw new UserRepositoryException(String.format("IN findByUsername there is no user found by userName %d", id));
    }
}
