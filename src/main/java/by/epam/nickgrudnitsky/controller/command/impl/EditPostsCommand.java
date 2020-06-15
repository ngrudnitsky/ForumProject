package by.epam.nickgrudnitsky.controller.command.impl;


import by.epam.nickgrudnitsky.controller.Action;
import by.epam.nickgrudnitsky.controller.command.Command;
import by.epam.nickgrudnitsky.entity.Post;
import by.epam.nickgrudnitsky.exception.ParameterValidationException;
import by.epam.nickgrudnitsky.exception.PostServiceException;
import by.epam.nickgrudnitsky.service.PostService;
import by.epam.nickgrudnitsky.service.impl.PostServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

import static by.epam.nickgrudnitsky.util.HttpUtil.*;


public class EditPostsCommand implements Command {
    private final PostService postService = new PostServiceImpl();

    @Override
    public Action execute(HttpServletRequest req, HttpServletResponse resp) {
        if (isMethodPost(req)) {
            try {
                final String pattern = "true";
                PostService postService = new PostServiceImpl();
                Integer id = Integer.parseInt(getRequestParameter(req, "id"));
                if (isRequestParameterPresented(req, "update", pattern)) {
                    updatePost(req, postService, id);
                }
                if (isRequestParameterPresented(req, "delete", pattern)) {
                    deletePost(postService, id);
                }
                loadAllPosts(req);
            } catch (ParameterValidationException e) {
                log.error("IN EditPostsCommand - failed to validate parameter.", e);
            } catch (PostServiceException e) {
                log.error("IN EditPostsCommand - failed to register user.");
            }
        }
        if (isMethodGet(req)) {
            try {
                loadAllPosts(req);
            } catch (PostServiceException e) {
                e.printStackTrace();
            }
        }
        return Action.EDIT_POST;
    }

    private void deletePost(PostService postService, Integer id) throws PostServiceException {
        postService.deleteById(id);
    }

    private void updatePost(HttpServletRequest req, PostService postService, Integer id) throws PostServiceException, ParameterValidationException {
        Post updatedPost = postService.findById(id);
        String title = getRequestParameter(req, "title");
        String content = getRequestParameter(req, "content");
        updatedPost.setTitle(title);
        updatedPost.setContent(content);
        postService.updatePost(updatedPost);
    }

    private void loadAllPosts(HttpServletRequest req) throws PostServiceException {
        List<Post> posts = postService.findAll();
        HttpSession session = req.getSession();
        session.setAttribute("posts", posts);
    }
}