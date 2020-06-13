package by.epam.nickgrudnitsky.controller.command.impl;

import by.epam.nickgrudnitsky.controller.Action;
import by.epam.nickgrudnitsky.controller.command.Command;
import by.epam.nickgrudnitsky.entity.Post;
import by.epam.nickgrudnitsky.exception.PostServiceException;
import by.epam.nickgrudnitsky.service.PostService;
import by.epam.nickgrudnitsky.service.impl.PostServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

public class MainPageCommand implements Command {
    @Override
    public Action execute(HttpServletRequest req, HttpServletResponse resp) {
        PostService postService = new PostServiceImpl();
        try {
            List<Post> posts = postService.findAll();
            req.setAttribute("posts", posts);
        } catch (PostServiceException e) {
            log.error(String.format("IN SignUpCommand - failed to validate parameter.%n%s", e.getMessage()));
            return Action.ERROR;
        }
        return Action.MAIN_PAGE;
    }
}
