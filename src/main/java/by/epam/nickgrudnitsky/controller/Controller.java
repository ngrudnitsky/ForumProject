package by.epam.nickgrudnitsky.controller;

import org.apache.commons.lang3.EnumUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Controller extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        executeCommand(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        executeCommand(req, resp);
    }

    public void executeCommand(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Action currentAction = define(req);
        Action next = currentAction.getCommand().execute(req, resp);
        if (next == null || next.equals(currentAction)) {
            resp.sendRedirect(req.getContextPath() + currentAction.getJspAddress());
        } else {
            resp.sendRedirect("do?command=" + next.name());
        }
    }

    private Action define(HttpServletRequest req) {
        Action result = Action.ERROR;
        String commandName = req.getParameter("command");
        if (commandName == null || commandName.isEmpty()) {
            return result;
        }
        return getCommand(commandName);
    }

    private Action getCommand(String input) {
        try {
            String commandName = input.toUpperCase().replace(" ", "_");
            return EnumUtils.getEnum(Action.class, commandName);
        } catch (IllegalArgumentException e) {
            return Action.ERROR;
        }
    }
}
