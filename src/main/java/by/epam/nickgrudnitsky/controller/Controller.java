package by.epam.nickgrudnitsky.controller;

import by.epam.nickgrudnitsky.controller.command.Command;
import by.epam.nickgrudnitsky.controller.command.impl.WrongCommand;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Controller extends HttpServlet {
    private final CommandProvider commandProvider = new CommandProvider();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        executeTask(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        executeTask(req, resp);
    }

    public void executeTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Command command = define(req);
        JSP jsp = command.execute(req, resp);
        forwardToJsp(req, resp, jsp);
    }

    private Command define(HttpServletRequest req) {
        Command result = new WrongCommand();
        String commandName = req.getParameter("command");
        if (commandName != null && !commandName.isEmpty()) {
            result = commandProvider.getCommand(commandName);
        }
        return result;
    }

    private void forwardToJsp(HttpServletRequest req, HttpServletResponse resp, JSP jsp) throws ServletException, IOException {
        ServletContext servletContext = req.getServletContext();
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(jsp.getJspAddress());
        requestDispatcher.forward(req, resp);
    }
}
