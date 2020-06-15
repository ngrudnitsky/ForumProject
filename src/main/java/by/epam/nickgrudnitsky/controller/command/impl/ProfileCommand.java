package by.epam.nickgrudnitsky.controller.command.impl;

import by.epam.nickgrudnitsky.controller.Action;
import by.epam.nickgrudnitsky.controller.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static by.epam.nickgrudnitsky.util.HttpUtil.*;


public class ProfileCommand implements Command {
    @Override
    public Action execute(HttpServletRequest req, HttpServletResponse resp) {
        final String pattern = "true";
        if (isMethodPost(req)) {
            if (isRequestParameterPresented(req, "logout", pattern)) {
                req.getSession().invalidate();
                return Action.LOG_IN;
            }
            if (isRequestParameterPresented(req, "create post", pattern)) {
                return Action.WRITING;
            }
            if (isRequestParameterPresented(req, "edit posts", pattern)) {
                return Action.EDIT_POST;
            }
        }
        return Action.PROFILE;
    }
}
