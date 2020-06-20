package by.epam.nickgrudnitsky.data;

import by.epam.nickgrudnitsky.entity.User;
import by.epam.nickgrudnitsky.exception.UserRepositoryException;

import java.util.List;

public interface UserRepository {
    User findByUsername(String name) throws UserRepositoryException;

    User create(User user) throws UserRepositoryException;

    User update(User user) throws UserRepositoryException;

    User deleteById(Integer id) throws UserRepositoryException;

    List<User> findAll() throws UserRepositoryException;

    User findById(Integer id) throws UserRepositoryException;
}
