package by.epam.nickgrudnitsky.controller.command.impl;

import by.epam.nickgrudnitsky.controller.Action;
import by.epam.nickgrudnitsky.controller.command.Command;
import by.epam.nickgrudnitsky.entity.dto.PostDTO;
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
    private final PostService postService = new PostServiceImpl();

    @Override
    public Action execute(HttpServletRequest req, HttpServletResponse resp) {
        if (isMethodPost(req)) {
            Post createdPost = new Post();
            createNewPost(req, createdPost);
            PostDTO post = PostDTO.convertToPostDTO(createdPost);
            User user = (User) req.getSession().getAttribute("user");
            post.setUser(user);
            setSessionAttribute(req, "post", post);
            return Action.POST;
        }
        return Action.WRITING;
    }

    private void createNewPost(HttpServletRequest req, Post post) {
        try {
            post.setTitle(getRequestParameter(req, "title"));
            post.setContent(getRequestParameter(req, "content"));
            User user = (User) req.getSession().getAttribute("user");
            post.setUserId(user.getId());
            postService.create(post);
        } catch (ParameterValidationException e) {
            log.error("IN WriteCommand.createNewPost - failed to validate parameter", e);
        } catch (PostServiceException e) {
            log.error("IN WriteCommand.createNewPost - failed to create new post", e);
        }
    }
}
