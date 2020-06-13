package by.epam.nickgrudnitsky.service.impl;

import by.epam.nickgrudnitsky.entity.Status;
import by.epam.nickgrudnitsky.entity.User;
import by.epam.nickgrudnitsky.exception.UserServiceException;
import by.epam.nickgrudnitsky.util.JdbcConnection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {
    private UserServiceImpl userService;
    private User user;

    @BeforeEach
    void initialize() {
        userService = new UserServiceImpl();
        user = new User();
        user.setFirstName("test first name");
        user.setLastName("test last name");
        user.setUserName("test user name");
        user.setEmail("test email");
        user.setPassword("test password");
    }

    @Test
    void register() throws UserServiceException {
        User registeredUser = userService.register(user);
        assertEquals(Status.ACTIVE, registeredUser.getStatus());
    }

    @Test
    void registerUserWithNull() {
        assertThrows(UserServiceException.class, () ->
                userService.register(null));
    }

    @Test
    void findAll() throws UserServiceException {
        List<User> users = userService.findAll();
        assertNotNull(users);
    }

    @Test
    void findByUsername() throws UserServiceException {
        User foundUser = userService.findByUsername("NickGS");
        assertNotNull(foundUser);
    }

    @Test
    void findByUsernameWithNullUsername()  {
        assertThrows(UserServiceException.class, () ->
                userService.findByUsername(null));
    }

    @Test
    void findByUsernameWithWrongId()  {
        assertThrows(UserServiceException.class, () ->
                userService.findByUsername("wrong username"));
    }

    @Test
    void findById() throws UserServiceException {
        User foundUser = userService.findById(1);
        assertNotNull(foundUser);
    }

    @Test
    void findByIdWithNullId()  {
        assertThrows(UserServiceException.class, () ->
                userService.findById(null));
    }

    @Test
    void findByIdWithWrongId()  {
        assertThrows(UserServiceException.class, () ->
                userService.findById(-1));
    }

    @Test
    void delete() throws UserServiceException {
        User deletedUser = userService.deleteById(1);
        assertEquals(Status.DELETED, deletedUser.getStatus());
    }

    @Test
    void deleteByIdWithNullId() {
        assertThrows(UserServiceException.class, () ->
                userService.deleteById(null));
    }

    @Test
    void deleteByIdWithWrongId() {
        assertThrows(UserServiceException.class, () ->
                userService.deleteById(-1));
    }

    @AfterAll
    static void resetDataBase() throws SQLException {
        JdbcConnection.reset();
    }
}