package by.epam.nickgrudnitsky.controller.command.impl;

import by.epam.nickgrudnitsky.controller.Action;
import by.epam.nickgrudnitsky.controller.command.Command;
import by.epam.nickgrudnitsky.entity.User;
import by.epam.nickgrudnitsky.exception.ParameterValidationException;
import by.epam.nickgrudnitsky.exception.UserServiceException;
import by.epam.nickgrudnitsky.service.UserService;
import by.epam.nickgrudnitsky.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static by.epam.nickgrudnitsky.util.HttpUtil.*;

public class SignUpCommand implements Command {
    private static final String NAME_PATTERN = "[A-Za-zА-Яа-я]+";
    private static final String USER_NAME_PATTERN = "[A-Za-z0-9А-Яа-я]+";
    private static final String EMAIL_PATTERN = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$";
    private static final String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";

    private final UserService userService = new UserServiceImpl();

    //todo password encoding
    @Override
    public Action execute(HttpServletRequest req, HttpServletResponse resp) {
        String sessionAttribute = "user";
        try {
            if (isMethodPost(req)) {
                User user = new User();
                user = registerUser(req, user);
                setSessionAttribute(req, sessionAttribute, user);
                return Action.PROFILE;
            }
        } catch (UserServiceException e) {
            log.error("IN SignUpCommand - failed to register user");
        } catch (ParameterValidationException e) {
            log.error("IN SignUpCommand - failed to validate parameter", e);
        }
        req.getSession().removeAttribute(sessionAttribute);
        return Action.JOIN;
    }

    private User registerUser(HttpServletRequest req, User user) throws ParameterValidationException, UserServiceException {
        user.setFirstName(getRequestParameter(req, "firstName", NAME_PATTERN));
        user.setLastName(getRequestParameter(req, "lastName", NAME_PATTERN));
        user.setUserName(getRequestParameter(req, "userName", USER_NAME_PATTERN));
        user.setEmail(getRequestParameter(req, "email", EMAIL_PATTERN));
        user.setPassword(getRequestParameter(req, "password", PASSWORD_PATTERN));
        return userService.register(user);
    }
}
