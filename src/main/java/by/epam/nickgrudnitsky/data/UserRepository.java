package by.epam.nickgrudnitsky.data;

import by.epam.nickgrudnitsky.entity.User;
import by.epam.nickgrudnitsky.exception.UserRepositoryException;

import java.sql.SQLException;
import java.util.List;

public interface UserRepository {
    User findByUsername(String name) throws SQLException, UserRepositoryException;

    User save(User user) throws SQLException;

    User update(User user) throws SQLException;

    List<User> findAll() throws SQLException, UserRepositoryException;

    User findById(Integer id) throws SQLException, UserRepositoryException;
}
