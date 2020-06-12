package by.epam.nickgrudnitsky.controller.command.impl;

import by.epam.nickgrudnitsky.controller.JSP;
import by.epam.nickgrudnitsky.controller.command.Command;
import by.epam.nickgrudnitsky.entity.User;
import by.epam.nickgrudnitsky.exception.UserServiceException;
import by.epam.nickgrudnitsky.service.UserService;
import by.epam.nickgrudnitsky.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignUpCommand implements Command {
    UserService userService = new UserServiceImpl();
    @Override
    public JSP execute(HttpServletRequest req, HttpServletResponse resp) {
        if (req.getMethod().equalsIgnoreCase("POST")){
            User user = new User();
            user.setFirstName(req.getParameter("firstName"));
            user.setLastName(req.getParameter("lastName"));
            user.setUserName(req.getParameter("userName"));
            user.setEmail(req.getParameter("email"));
            user.setPassword(req.getParameter("password"));
            try {
                userService.register(user);
            } catch (UserServiceException e) {
                log.error("IN execute - failed to register user.");
                return JSP.SIGN_UP;
            }
        }
        return JSP.INDEX;
    }
}
