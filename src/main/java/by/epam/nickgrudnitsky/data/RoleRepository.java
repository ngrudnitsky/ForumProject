package by.epam.nickgrudnitsky.data;

import by.epam.nickgrudnitsky.entity.Role;
import by.epam.nickgrudnitsky.exception.RoleRepositoryException;

import java.util.List;

public interface RoleRepository {
    Role findByName(String name) throws RoleRepositoryException;

    List<String> findAllUserRoles(Integer userId) throws RoleRepositoryException;

    void setRoleUser(Integer userId) throws RoleRepositoryException;
}
