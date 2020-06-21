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
            PostDTO post = createNewPost(req);
            if (post != null) {
                setSessionAttribute(req, "post", post);
                setSessionAttribute(req, "postId", post.getId());
                return Action.POST;
            }
        }
        return Action.WRITING;
    }

    private PostDTO createNewPost(HttpServletRequest req) {
        try {
            Post post = new Post();
            post.setTitle(getRequestParameter(req, "title"));
            post.setContent(getRequestParameter(req, "content"));
            User user = (User) req.getSession().getAttribute("user");
            post.setUserId(user.getId());
            postService.create(post);
            PostDTO postDto = PostDTO.convertToPostDTO(post);
            postDto.setUser(user);
            return postDto;
        } catch (ParameterValidationException e) {
            log.error("IN WriteCommand.createNewPost - failed to validate parameter", e);
        } catch (PostServiceException e) {
            log.error("IN WriteCommand.createNewPost - failed to create new post", e);
        }
        return null;
    }
}
