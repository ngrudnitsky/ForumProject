package by.epam.nickgrudnitsky.service;

import by.epam.nickgrudnitsky.entity.User;
import by.epam.nickgrudnitsky.exception.UserServiceException;

import java.util.List;

public interface UserService {
    User register(User user) throws UserServiceException;

    List<User> getAll() throws UserServiceException;

    User findByUsername(String username) throws UserServiceException;

    User findById(Integer id) throws UserServiceException;

    void delete(Integer id) throws UserServiceException;
}
