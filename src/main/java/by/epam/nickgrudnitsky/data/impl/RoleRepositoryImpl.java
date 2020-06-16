package by.epam.nickgrudnitsky.data.impl;

import by.epam.nickgrudnitsky.entity.User;
import by.epam.nickgrudnitsky.util.JdbcConnection;
import by.epam.nickgrudnitsky.data.RoleRepository;
import by.epam.nickgrudnitsky.entity.Role;
import by.epam.nickgrudnitsky.entity.Status;
import by.epam.nickgrudnitsky.exception.RoleRepositoryException;

import org.apache.commons.lang3.EnumUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleRepositoryImpl implements RoleRepository {
    private static final String FIND_BY_NAME_QUERY = "SELECT * FROM roles WHERE name = '%s'";
    private static final String FIND_ALL_USER_ROLES_QUERY = "SELECT roles_id FROM users_has_roles WHERE users_id = ?";
    private static final String SAVE_USER_ROLE_QUERY = "INSERT INTO users_has_roles(users_id, roles_id)VALUES(?,?)";

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

    @Override
    public List<String> findUserRoles(User user) throws RoleRepositoryException {
        try {
            List<String> roles = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_USER_ROLES_QUERY);
            preparedStatement.setInt(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                roles.add(resultSet.getString(1));
            }
            return roles;
        } catch (SQLException e) {
            throw new RoleRepositoryException("IN RoleRepositoryImpl failed to find all user's roles.", e);
        }
    }

    @Override
    public User setUserRole(User user) throws RoleRepositoryException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SAVE_USER_ROLE_QUERY);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setInt(2, 1);
            preparedStatement.executeUpdate();
            return user;
        } catch (SQLException e) {
            throw new RoleRepositoryException(
                    String.format("IN RoleRepositoryImpl failed to save user's %s role USER", user), e);
        }
    }
}
