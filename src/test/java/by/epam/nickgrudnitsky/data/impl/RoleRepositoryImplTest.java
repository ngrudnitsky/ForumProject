package by.epam.nickgrudnitsky.data.impl;

import by.epam.nickgrudnitsky.entity.Role;
import by.epam.nickgrudnitsky.exception.RoleRepositoryException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class RoleRepositoryImplTest
{
    private RoleRepositoryImpl roleRepository;

    @BeforeEach
    void initialize() throws SQLException
    {
        roleRepository = new RoleRepositoryImpl();
    }

    @Test
    void findRoleByName() throws SQLException, RoleRepositoryException
    {
        Role roleUser = roleRepository.findByName("USER");
        assertEquals(roleUser.getName(), "USER");
    }

    @Test
    void findRoleByNameWithWrongName()
    {
        assertThrows(RoleRepositoryException.class, () ->
                roleRepository.findByName("wrongName"));
    }
}