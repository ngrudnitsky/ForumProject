package by.epam.nickgrudnitsky.data.impl;

import by.epam.nickgrudnitsky.JdbcConnection;
import by.epam.nickgrudnitsky.data.RoleRepository;
import by.epam.nickgrudnitsky.entity.Role;
import by.epam.nickgrudnitsky.entity.Status;
import org.apache.commons.lang3.EnumUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleRepositoryImpl implements RoleRepository {
    private static Connection connection;

    public RoleRepositoryImpl() throws SQLException {
        connection = JdbcConnection.getConnection();
    }
    @Override
    public Role findByName(String roleName) throws SQLException {
        Role role;
        ResultSet resultSet =
                connection.createStatement().executeQuery(String.format("SELECT * FROM roles WHERE name = '%s'", roleName));
        if (resultSet.next()) {
            role = new Role();
            role.setId(resultSet.getInt("id"));
            role.setName(roleName);
            role.setStatus(EnumUtils.getEnum(Status.class, resultSet.getString("status")));
            role.setCreated(resultSet.getDate("createdAt"));
            role.setUpdated(resultSet.getDate("updatedAt"));
            return role;
        }

        //todo
        throw new SQLException();
    }
}
