package by.epam.nickgrudnitsky.controller.command.impl;


import by.epam.nickgrudnitsky.controller.Action;
import by.epam.nickgrudnitsky.controller.command.Command;
import by.epam.nickgrudnitsky.entity.dto.PostDTO;
import by.epam.nickgrudnitsky.exception.PostServiceException;
import by.epam.nickgrudnitsky.exception.UserServiceException;
import by.epam.nickgrudnitsky.service.PostService;
import by.epam.nickgrudnitsky.service.UserService;
import by.epam.nickgrudnitsky.service.impl.PostServiceImpl;
import by.epam.nickgrudnitsky.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static by.epam.nickgrudnitsky.util.HttpUtil.*;


public class PostCommand implements Command {
    @Override
    public Action execute(HttpServletRequest req, HttpServletResponse resp) {
        if (isMethodGet(req)) {
            Integer id = Integer.parseInt(req.getParameter("postId"));
            try {
                PostService postService = new PostServiceImpl();
                UserService userService = new UserServiceImpl();
                PostDTO post = PostDTO.convertToPostDTO(postService.findById(id));
                post.setUser(userService.findById(post.getUserId()));
                setSessionAttribute(req,"post", post);
            } catch (PostServiceException e) {
                log.error(String.format("IN PostCommand - failed to find post by id.%n%s", e.getMessage()));
            } catch (UserServiceException e) {
                log.error(String.format("IN PostCommand - failed to find user by id.%n%s", e.getMessage()));
            }
        }
        return Action.POST;
    }
}