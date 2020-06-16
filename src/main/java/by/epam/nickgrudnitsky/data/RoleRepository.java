package by.epam.nickgrudnitsky.data;

import by.epam.nickgrudnitsky.entity.Role;
import by.epam.nickgrudnitsky.entity.User;
import by.epam.nickgrudnitsky.exception.RoleRepositoryException;

import java.util.List;

public interface RoleRepository {
    Role findByName(String name) throws RoleRepositoryException;

    List<String> findUserRoles(User user) throws RoleRepositoryException;

    User setUserRole(User user) throws RoleRepositoryException;
}
