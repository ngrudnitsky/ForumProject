package by.epam.nickgrudnitsky.controller.command;

import by.epam.nickgrudnitsky.controller.JSP;
import by.epam.nickgrudnitsky.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
    Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    JSP execute(HttpServletRequest req, HttpServletResponse resp);
}