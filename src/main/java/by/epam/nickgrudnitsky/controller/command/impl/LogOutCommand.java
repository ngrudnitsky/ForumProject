package by.epam.nickgrudnitsky.controller.command.impl;

import by.epam.nickgrudnitsky.controller.Action;
import by.epam.nickgrudnitsky.controller.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogOutCommand implements Command {
    @Override
    public Action execute(HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().invalidate();
        return Action.LOG_IN;
    }
}
