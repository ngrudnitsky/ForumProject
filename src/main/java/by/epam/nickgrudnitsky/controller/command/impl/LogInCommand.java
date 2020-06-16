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
import javax.servlet.http.HttpSession;

import static by.epam.nickgrudnitsky.util.HttpUtil.*;

public class LogInCommand implements Command {
    private static final String USER_NAME_PATTERN = "[A-Za-z0-9А-Яа-я]+";
    private static final String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";

    private final UserService userService = new UserServiceImpl();

    @Override
    public Action execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            if (isMethodPost(req)) {
                String userName = getRequestParameter(req, "username", USER_NAME_PATTERN);
                String password = getRequestParameter(req, "password", PASSWORD_PATTERN);

                User user = userService.findByUsername(userName);
                if (user.getPassword().equals(password)) {
                    HttpSession userSession = setSessionAttribute(req, "user", user);
                    userSession.setMaxInactiveInterval(600);
                    HttpSession adminSession = setSessionAttribute(req, "admin", userService.checkIfAdmin(user) ? "true" : "false");
                    adminSession.setMaxInactiveInterval(600);
                    return Action.PROFILE;
                }
            }
        } catch (ParameterValidationException e) {
            log.error(String.format("IN LogInCommand - failed to validate parameter.%n%s", e.getMessage()));
        } catch (UserServiceException e) {
            log.error(String.format("IN LogInCommand - failed to find user.%n%s", e.getMessage()));
        }
        return Action.LOG_IN;
    }
}
