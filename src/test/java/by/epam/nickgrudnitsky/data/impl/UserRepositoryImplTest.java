package by.epam.nickgrudnitsky.data.impl;

import by.epam.nickgrudnitsky.entity.Status;
import by.epam.nickgrudnitsky.entity.User;
import by.epam.nickgrudnitsky.exception.UserRepositoryException;

import by.epam.nickgrudnitsky.util.JdbcConnection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryImplTest {
    private UserRepositoryImpl userRepository;
    private User user;

    @BeforeEach
    void initialize() {
        userRepository = new UserRepositoryImpl();
        user = new User();
        user.setId(-1);
        user.setFirstName("test");
        user.setLastName("test");
        user.setUserName("test");
        user.setPassword("test");
        user.setEmail("test");
        user.setStatus(Status.ACTIVE);
        user.setCreated(new Date());
        user.setUpdated(new Date());
    }

    @Test
    void findByUsername() throws UserRepositoryException {
        String userName = "NickGS";
        User nickGS = userRepository.findByUsername(userName);
        assertEquals(nickGS.getUserName(), userName);
    }

    @Test
    void findByUsernameWithWrongName() {
        assertThrows(UserRepositoryException.class, () ->
                userRepository.findByUsername("vghhh"));
    }

    @Test
    void save() throws UserRepositoryException {
        User savesUser = userRepository.create(user);
        assertNotNull(savesUser);
    }

    @Test
    void findAll() throws UserRepositoryException {
        List<User> users = userRepository.findAll();
        assertNotNull(users);
    }

    @Test
    void update() throws UserRepositoryException {
        User savesUser = userRepository.update(user);
        assertNotNull(savesUser);
    }

    @Test
    void findById() throws UserRepositoryException {
        int id = 1;
        User nickGS = userRepository.findById(id);
        assertEquals(nickGS.getId(), id);
    }

    @Test
    void findByIdWithWrongId() {
        assertThrows(UserRepositoryException.class, () ->
                userRepository.findById(Integer.MAX_VALUE));
    }

    @Test
    void deleteUserById() throws UserRepositoryException {
        int id = 1;
        User user = userRepository.deleteById(id);
        assertEquals(Status.DELETED, user.getStatus());
    }

    @Test
    void deleteUserByIdWithWrongId() {
        assertThrows(UserRepositoryException.class, () ->
                userRepository.deleteById(Integer.MAX_VALUE));
    }

    @AfterAll
    static void resetDataBase() throws SQLException {
        JdbcConnection.reset();
    }
}