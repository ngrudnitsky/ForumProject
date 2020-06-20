package by.epam.nickgrudnitsky.data.impl;

import by.epam.nickgrudnitsky.entity.Role;
import by.epam.nickgrudnitsky.entity.Status;
import by.epam.nickgrudnitsky.entity.User;
import by.epam.nickgrudnitsky.exception.RoleRepositoryException;

import by.epam.nickgrudnitsky.util.JdbcConnection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoleRepositoryImplTest {
    private RoleRepositoryImpl roleRepository;

    @BeforeEach
    void initialize() {
        roleRepository = new RoleRepositoryImpl();
    }

    @Test
    void findRoleByName() throws RoleRepositoryException {
        Role roleUser = roleRepository.findByName("USER");
        assertEquals(roleUser.getName(), "USER");
    }

    @Test
    void findRoleByNameWithWrongName() {
        assertThrows(RoleRepositoryException.class, () ->
                roleRepository.findByName("wrongName"));
    }

    @Test
    void findAllUserRoles() throws RoleRepositoryException {
        List<String> roles = roleRepository.findAllUserRoles(1);
        assertAll(
                () -> assertEquals("1", roles.get(0)),
                () -> assertEquals("2", roles.get(1))
        );
    }

    @Test
    void findAllUserRolesWithNonexistentUser() {
        assertThrows(RoleRepositoryException.class, () ->
                roleRepository.findAllUserRoles(-1));
    }

    @Test
    void setRoleUser() throws RoleRepositoryException {
        Integer userId = 100_000_000;
        roleRepository.setRoleUser(userId);
        List<String> allUserRoles = roleRepository.findAllUserRoles(userId);
        assertTrue(allUserRoles.contains("1"));
    }

    @AfterAll
    static void resetDataBase() throws SQLException {
        JdbcConnection.reset();
    }
}