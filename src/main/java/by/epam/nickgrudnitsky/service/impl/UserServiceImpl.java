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
            log.error("IN register - user is null");
            throw new UserServiceException("Null user tried to be registered.");
        }
        Role roleUser;
        try {
            roleUser = roleRepository.findByName("USER");
        } catch (RoleRepositoryException e) {
            log.error("IN register - there is no role User", e);
            throw new UserServiceException(e.getMessage(), e);
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
            log.error("IN register - failed to save user");
            throw new UserServiceException("Failed to save user.", e);
        }
        log.info("IN register - user: {} successfully registered", registeredUser);
        return registeredUser;
    }

    @Override
    public List<User> getAll() throws UserServiceException {
        List<User> result;
        try {
            result = userRepository.findAll();
        } catch (UserRepositoryException e) {
            log.error("IN getAll - failed to get all users");
            throw new UserServiceException("Failed to get all users", e);
        }
        log.info("IN getAll - {} users found", result.size());
        return result;
    }

    @Override
    public User findByUsername(String username) throws UserServiceException {
        User result;
        if (username == null) {
            throw new UserServiceException("Null userName was found");
        }
        try {
            result = userRepository.findByUsername(username);
        } catch (UserRepositoryException e) {
            log.error("IN findByUsername - failed to find user by userName {}", username);
            throw new UserServiceException(String.format("Failed to find user by userName %s", username), e);
        }
        log.info("IN findByUsername - user: {} found by username: {}", result, username);
        return result;
    }

    @Override
    public User findById(Integer id) throws UserServiceException {
        User result;
        if (id == null) {
            throw new UserServiceException("Null id was found");
        }
        try {
            result = userRepository.findById(id);
        } catch (UserRepositoryException e) {
            log.error("IN findById - failed to find user by id {}", id);
            throw new UserServiceException(String.format("Failed to find user by id %s", id), e);
        }
        log.info("IN findById - user: {} found by id: {}", result, id);
        return result;
    }

    @Override
    public void delete(Integer id) throws UserServiceException {
        if (id == null) {
            throw new UserServiceException("Null userName was found");
        }
        User user;
        try {
            user = userRepository.findById(id);
        } catch (UserRepositoryException e) {
            log.error("IN delete - failed to delete user by id {}", id);
            throw new UserServiceException(String.format("Failed to delete user by id %s", id), e);
        }
        user.setStatus(Status.DELETED);
        user.setUpdated(new Date());
        try {
            userRepository.update(user);
        } catch (UserRepositoryException e) {
            log.error("IN delete - failed to update user by id {}", id);
            throw new UserServiceException(String.format("Failed to delete user by id %s", id), e);
        }
        log.info("IN delete - user with id: {} successfully deleted", id);
    }
}
