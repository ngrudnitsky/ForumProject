package by.epam.nickgrudnitsky.service.impl;

import by.epam.nickgrudnitsky.entity.User;
import by.epam.nickgrudnitsky.exception.PostServiceException;
import by.epam.nickgrudnitsky.exception.UserServiceException;

import java.util.List;

public interface UserService {
    User register(User user) throws UserServiceException;

    List<User> findAll() throws UserServiceException;

    User findByUsername(String username) throws UserServiceException;

    User findById(Integer id) throws UserServiceException;

    User deleteById(Integer id) throws UserServiceException;

    boolean checkIfAdmin(Integer id) throws UserServiceException;

    Integer getLastId() throws PostServiceException;
}
