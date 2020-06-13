package by.epam.nickgrudnitsky.controller.command.impl;

import by.epam.nickgrudnitsky.controller.Action;
import by.epam.nickgrudnitsky.controller.command.Command;
import by.epam.nickgrudnitsky.entity.Post;
import by.epam.nickgrudnitsky.entity.User;
import by.epam.nickgrudnitsky.exception.ParameterValidationException;
import by.epam.nickgrudnitsky.exception.PostServiceException;
import by.epam.nickgrudnitsky.service.PostService;
import by.epam.nickgrudnitsky.service.impl.PostServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import static by.epam.nickgrudnitsky.util.HttpUtil.*;

public class WritePostCommand implements Command {
    private static final String NAME_PATTERN = "[A-Za-zА-Яа-я]+";

    private final PostService postService = new PostServiceImpl();

    @Override
    public Action execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            if (isMethodPost(req)) {
                Post post = new Post();
                createNewPost(req, post);
                return Action.MAIN_PAGE;
            }
        } catch (ParameterValidationException e) {
            log.error(String.format("IN SignUpCommand - failed to validate parameter.%n%s", e.getMessage()));
        } catch (PostServiceException e) {
            e.printStackTrace();
        }
        return Action.WRITING;
    }

    private void createNewPost(HttpServletRequest req, Post post)
            throws ParameterValidationException, PostServiceException {
        post.setTitle(getRequestParameter(req, "title", NAME_PATTERN));
        post.setContent(getRequestParameter(req, "content", NAME_PATTERN));
        User user = (User)req.getSession().getAttribute("user");
        post.setUserId(user.getId());
        postService.create(post);
    }
}
