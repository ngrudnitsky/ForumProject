package by.epam.nickgrudnitsky.data.impl;

import by.epam.nickgrudnitsky.util.JdbcConnection;
import by.epam.nickgrudnitsky.data.RoleRepository;
import by.epam.nickgrudnitsky.entity.Role;
import by.epam.nickgrudnitsky.entity.Status;
import by.epam.nickgrudnitsky.exception.RoleRepositoryException;

import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleRepositoryImpl implements RoleRepository {
    private final Logger log = LoggerFactory.getLogger(RoleRepositoryImpl.class);
    private final Connection connection = JdbcConnection.getConnection();

    @Override
    public Role findByName(String roleName) throws RoleRepositoryException {
        String findByNameQuery = String.format("SELECT * FROM roles WHERE name = '%s'", roleName);
        String errorMessage = "IN RoleRepositoryImpl.findByName failed to find role by name %s";
        Role role = new Role();
        try {
            ResultSet resultSet = connection.createStatement().executeQuery(findByNameQuery);
            if (resultSet.next()) {
                role.setId(resultSet.getInt("id"));
                role.setName(roleName);
                role.setStatus(EnumUtils.getEnum(Status.class, resultSet.getString("status")));
                role.setCreated(resultSet.getDate("createdAt"));
                role.setUpdated(resultSet.getDate("updatedAt"));
                return role;
            }
        } catch (SQLException e) {
            log.error(errorMessage);
            throw new RoleRepositoryException(errorMessage, e);
        }
        log.error(errorMessage);
        throw new RoleRepositoryException(errorMessage);
    }

    //todo List<Integer>
    @Override
    public List<String> findAllUserRoles(Integer userId) throws RoleRepositoryException {
        String errorMessage = String.format(
                "IN RoleRepositoryImpl.findAllUserRoles failed to find all roles of %s user.", userId);
        try {
            String findAllUserRolesQuery = String.format(
                    "SELECT roles_id FROM users_has_roles WHERE users_id = %s", userId);
            List<String> roles = new ArrayList<>();
            ResultSet resultSet = connection.createStatement().executeQuery(findAllUserRolesQuery);
            while (resultSet.next()) {
                roles.add(resultSet.getString(1));
            }
            if (roles.isEmpty()){
                log.error(errorMessage);
                throw new RoleRepositoryException(errorMessage);
            }
            return roles;
        } catch (SQLException e) {
            log.error(errorMessage);
            throw new RoleRepositoryException(errorMessage, e);
        }
    }

    @Override
    public void setRoleUser(Integer userId) throws RoleRepositoryException {
        try {
            String saveUserRoleQuery = String.format(
                    "INSERT INTO users_has_roles(users_id, roles_id)VALUES(%s,1)", userId);
            connection.createStatement().executeUpdate(saveUserRoleQuery);
        } catch (SQLException e) {
            String errorMessage = String.format(
                    "IN RoleRepositoryImpl.setRoleUser failed to save user's USER with id %s", userId);
            log.error(errorMessage);
            throw new RoleRepositoryException(errorMessage, e);
        }
    }
}
