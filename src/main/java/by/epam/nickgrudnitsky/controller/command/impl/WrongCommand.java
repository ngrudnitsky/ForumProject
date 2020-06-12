package by.epam.nickgrudnitsky.controller.command.impl;

import by.epam.nickgrudnitsky.controller.JSP;
import by.epam.nickgrudnitsky.controller.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WrongCommand implements Command {
    public WrongCommand() {
    }

    @Override
    public JSP execute(HttpServletRequest req, HttpServletResponse resp) {
        return JSP.ERROR;
    }
}
