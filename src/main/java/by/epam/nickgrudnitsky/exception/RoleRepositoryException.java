package by.epam.nickgrudnitsky.exception;

public class RoleRepositoryException extends Exception {
    public RoleRepositoryException() {
    }

    public RoleRepositoryException(String message) {
        super(message);
    }

    public RoleRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public RoleRepositoryException(Throwable cause) {
        super(cause);
    }
}
