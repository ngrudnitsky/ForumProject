package by.epam.nickgrudnitsky.data;

import by.epam.nickgrudnitsky.entity.Role;
import by.epam.nickgrudnitsky.exception.RoleRepositoryException;

import java.sql.SQLException;

public interface RoleRepository {
    Role findByName(String name) throws SQLException, RoleRepositoryException;
}
