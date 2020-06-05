package by.epam.nickgrudnitsky.data.impl;

import by.epam.nickgrudnitsky.entity.Role;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class RoleRepositoryImplTest {

    @Test
    void findRoleByName() {
        try {
            RoleRepositoryImpl roleRepository = new RoleRepositoryImpl();
            Role roleUser = roleRepository.findByName("USER");
            assertEquals(roleUser.getName(), "USER");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}