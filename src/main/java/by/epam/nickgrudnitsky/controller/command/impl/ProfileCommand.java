package by.epam.nickgrudnitsky.controller.command.impl;

import by.epam.nickgrudnitsky.controller.Action;
import by.epam.nickgrudnitsky.controller.command.Command;
import by.epam.nickgrudnitsky.exception.ParameterValidationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static by.epam.nickgrudnitsky.util.HttpUtil.*;


public class ProfileCommand implements Command {
    private static final String LOGOUT_PARAMETER = "logout";
    private static final String LOGOUT_PATTERN = "true";

    @Override
    public Action execute(HttpServletRequest req, HttpServletResponse resp) {
        if (isMethodGet(req) && isUserLoggedIn(req)) {
            return Action.PROFILE;
        }
        try {
            if (isMethodPost(req) &&
                    getRequestParameter(req, LOGOUT_PARAMETER, LOGOUT_PATTERN).equalsIgnoreCase(LOGOUT_PATTERN)) {
                req.getSession().invalidate();
            }
        } catch (ParameterValidationException e) {
            log.error(String.format("IN SignUpCommand - failed to validate parameter.%n%s", e.getMessage()));
        }
        return Action.LOG_IN;
    }
}
