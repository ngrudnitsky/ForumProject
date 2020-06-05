package by.epam.nickgrudnitsky.service.impl;

import by.epam.nickgrudnitsky.data.RoleRepository;
import by.epam.nickgrudnitsky.data.UserRepository;
import by.epam.nickgrudnitsky.entity.Role;
import by.epam.nickgrudnitsky.entity.Status;
import by.epam.nickgrudnitsky.entity.User;
import by.epam.nickgrudnitsky.exception.RoleRepositoryException;
import by.epam.nickgrudnitsky.exception.UserRepositoryException;
import by.epam.nickgrudnitsky.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User register(User user) throws SQLException, RoleRepositoryException {
        if (user == null)
            return null;

        Role roleUser = roleRepository.findByName("USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);
        user.setCreated(new Date());
        user.setUpdated(new Date());

        User registeredUser = userRepository.save(user);

        log.info("IN register - user: {} successfully registered", registeredUser);

        return registeredUser;
    }

    @Override
    public List<User> getAll() throws SQLException, UserRepositoryException {
        List<User> result = userRepository.findAll();
        log.info("IN getAll - {} users found", result.size());
        return result;
    }

    @Override
    public User findByUsername(String username) throws SQLException, UserRepositoryException {
        User result = userRepository.findByUsername(username);

        if (result == null) {
            log.warn("IN findByUsername - no user found by username: {}", username);
            return null;
        }

        log.info("IN findByUsername - user: {} found by username: {}", result, username);
        return result;
    }

    @Override
    public User findById(Integer id) throws SQLException, UserRepositoryException {
        User result = userRepository.findById(id);

        if (result == null) {
            log.warn("IN findById - no user found by id: {}", id);
            return null;
        }

        log.info("IN findById - user: {} found by id: {}", result, id);
        return result;
    }

    @Override
    public void delete(Integer id) throws SQLException, UserRepositoryException {
        User user = userRepository.findById(id);
        if (user == null) {
            log.warn("IN delete - no user found by id: {}", id);
            return;
        }
        user.setStatus(Status.DELETED);
        user.setUpdated(new Date());
        userRepository.update(user);
        log.info("IN delete - user with id: {} successfully deleted", id);
    }
}
