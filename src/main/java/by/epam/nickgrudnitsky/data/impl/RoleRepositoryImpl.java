package by.epam.nickgrudnitsky.data.impl;

import by.epam.nickgrudnitsky.util.JdbcConnection;
import by.epam.nickgrudnitsky.data.RoleRepository;
import by.epam.nickgrudnitsky.entity.Role;
import by.epam.nickgrudnitsky.entity.Status;
import by.epam.nickgrudnitsky.exception.RoleRepositoryException;

import org.apache.commons.lang3.EnumUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleRepositoryImpl implements RoleRepository {
    private static final String FIND_BY_NAME_QUERY = "SELECT * FROM roles WHERE name = '%s'";

    private Connection connection = JdbcConnection.getConnection();

    @Override
    public Role findByName(String roleName) throws RoleRepositoryException {
        String ERROR_MESSAGE = "IN RoleRepositoryImpl failed to find role by name %s";
        try {
            Role role = new Role();
            ResultSet resultSet = connection.createStatement().executeQuery(String.format(FIND_BY_NAME_QUERY, roleName));
            if (resultSet.next()) {
                role.setId(resultSet.getInt("id"));
                role.setName(roleName);
                role.setStatus(EnumUtils.getEnum(Status.class, resultSet.getString("status")));
                role.setCreated(resultSet.getDate("createdAt"));
                role.setUpdated(resultSet.getDate("updatedAt"));
                return role;
            }
        } catch (SQLException e) {
            throw new RoleRepositoryException(
                    String.format(ERROR_MESSAGE, roleName), e);
        }
        throw new RoleRepositoryException(
                String.format(ERROR_MESSAGE, roleName));
    }
}
