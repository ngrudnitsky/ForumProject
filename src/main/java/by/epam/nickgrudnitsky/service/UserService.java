package by.epam.nickgrudnitsky.service;

import by.epam.nickgrudnitsky.entity.User;
import by.epam.nickgrudnitsky.exception.RoleRepositoryException;
import by.epam.nickgrudnitsky.exception.UserRepositoryException;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    User register(User user) throws SQLException, RoleRepositoryException;

    List<User> getAll() throws SQLException, UserRepositoryException;

    User findByUsername(String username) throws SQLException, UserRepositoryException;

    User findById(Integer id) throws SQLException, UserRepositoryException;

    void delete(Integer id) throws SQLException, UserRepositoryException;
}
