package by.epam.nickgrudnitsky.controller.command.impl;

import by.epam.nickgrudnitsky.controller.Action;
import by.epam.nickgrudnitsky.controller.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static by.epam.nickgrudnitsky.util.HttpUtil.*;


public class ProfileCommand implements Command {
    private static final String LOGOUT_PARAMETER = "logout";
    private static final String CREATE_POST_PARAMETER = "create post";
    private static final String PATTERN = "true";

    @Override
    public Action execute(HttpServletRequest req, HttpServletResponse resp) {
        if (isMethodGet(req) && isUserLoggedIn(req)) {
            return Action.PROFILE;
        }
        if (isMethodPost(req)) {
            if (isRequestParameterPresented(req, LOGOUT_PARAMETER, PATTERN)) {
                req.getSession().invalidate();
            }
            if (isRequestParameterPresented(req, CREATE_POST_PARAMETER, PATTERN)) {
                return Action.WRITING;
            }
        }
        return Action.LOG_IN;
    }
}
