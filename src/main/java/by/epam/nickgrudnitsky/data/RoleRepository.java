package by.epam.nickgrudnitsky.data;

import by.epam.nickgrudnitsky.entity.Role;
import by.epam.nickgrudnitsky.exception.RoleRepositoryException;

public interface RoleRepository {
    Role findByName(String name) throws RoleRepositoryException;
}
