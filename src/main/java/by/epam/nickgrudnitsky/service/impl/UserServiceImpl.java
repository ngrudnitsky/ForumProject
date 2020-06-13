package by.epam.nickgrudnitsky.service.impl;

import by.epam.nickgrudnitsky.data.RoleRepository;
import by.epam.nickgrudnitsky.data.UserRepository;
import by.epam.nickgrudnitsky.data.impl.RoleRepositoryImpl;
import by.epam.nickgrudnitsky.data.impl.UserRepositoryImpl;
import by.epam.nickgrudnitsky.entity.Role;
import by.epam.nickgrudnitsky.entity.Status;
import by.epam.nickgrudnitsky.entity.User;
import by.epam.nickgrudnitsky.exception.RoleRepositoryException;
import by.epam.nickgrudnitsky.exception.UserRepositoryException;
import by.epam.nickgrudnitsky.exception.UserServiceException;
import by.epam.nickgrudnitsky.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository = new UserRepositoryImpl();
    private final RoleRepository roleRepository = new RoleRepositoryImpl();
    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User register(User user) throws UserServiceException {
        if (user == null) {
            String errorMessage = "IN UserServiceImpl.register - user is null";
            log.error(errorMessage);
            throw new UserServiceException(errorMessage);
        }
        Role roleUser;
        try {
            roleUser = roleRepository.findByName("USER");
        } catch (RoleRepositoryException e) {
            String errorMessage = "IN UserServiceImpl.register - there is no role USER";
            log.error(errorMessage, e);
            throw new UserServiceException(errorMessage, e);
        }
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);
        user.setCreated(new Date());
        user.setUpdated(new Date());
        User registeredUser;
        try {
            registeredUser = userRepository.save(user);
        } catch (UserRepositoryException e) {
            String errorMessage = "IN UserServiceImpl.register - failed to register user";
            log.error(errorMessage);
            throw new UserServiceException(errorMessage, e);
        }
        log.info("IN UserServiceImpl.register - user: {} successfully registered", registeredUser);
        return registeredUser;
    }

    @Override
    public List<User> findAll() throws UserServiceException {
        List<User> result;
        try {
            result = userRepository.findAll();
        } catch (UserRepositoryException e) {
            String errorMessage = "IN UserServiceImpl.getAll - failed to get all users";
            log.error(errorMessage);
            throw new UserServiceException(errorMessage, e);
        }
        log.info("IN UserServiceImpl.getAll - {} users found", result.size());
        return result;
    }

    @Override
    public User findByUsername(String username) throws UserServiceException {
        User result;
        if (username == null) {
            String errorMessage = "IN UserServiceImpl.findByUsername - Null userName was found";
            log.error(errorMessage);
            throw new UserServiceException(errorMessage);
        }
        try {
            result = userRepository.findByUsername(username);
        } catch (UserRepositoryException e) {
            String errorMessage = String.format(
                    "IN UserServiceImpl.findByUsername - Failed to find user by userName %s", username);
            log.error(errorMessage);
            throw new UserServiceException(errorMessage);
        }
        log.info("IN UserServiceImpl.findByUsername - user: {} found by username: {}", result, username);
        return result;
    }

    @Override
    public User findById(Integer id) throws UserServiceException {
        User result;
        if (id == null) {
            String errorMessage = "IN UserServiceImpl.findById - Null id was found";
            log.error(errorMessage);
            throw new UserServiceException(errorMessage);
        }
        try {
            result = userRepository.findById(id);
        } catch (UserRepositoryException e) {
            String errorMessage = String.format("IN UserServiceImpl.findById - Failed to find user by id %s", id);
            log.error(errorMessage);
            throw new UserServiceException(errorMessage, e);
        }
        log.info("IN UserServiceImpl.findById - user: {} found by id: {}", result, id);
        return result;
    }

    @Override
    public User deleteById(Integer id) throws UserServiceException {
        if (id == null) {
            String errorMessage = "IN UserServiceImpl.delete - Null id was found";
            log.error(errorMessage);
            throw new UserServiceException(errorMessage);
        }
        User user;
        try {
            user = userRepository.findById(id);
        } catch (UserRepositoryException e) {
            String errorMessage = String.format("IN UserServiceImpl.delete - Failed to delete user by id %s", id);
            log.error(errorMessage);
            throw new UserServiceException(errorMessage, e);
        }
        user.setStatus(Status.DELETED);
        user.setUpdated(new Date());
        try {
            userRepository.update(user);
        } catch (UserRepositoryException e) {
            String errorMessage = String.format("IN UserServiceImpl.delete - Failed to delete user by id %s", id);
            log.error(errorMessage);
            throw new UserServiceException(errorMessage, e);
        }
        log.info("IN UserServiceImpl.delete - user with id: {} successfully deleted", id);
        return user;
    }
}
