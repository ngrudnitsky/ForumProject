package by.epam.nickgrudnitsky.exception;

public class ParameterValidationException extends Exception {
    public ParameterValidationException() {
    }

    public ParameterValidationException(String message) {
        super(message);
    }

    public ParameterValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParameterValidationException(Throwable cause) {
        super(cause);
    }
}
