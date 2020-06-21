package by.epam.nickgrudnitsky.controller.command.impl;

import by.epam.nickgrudnitsky.controller.Action;
import by.epam.nickgrudnitsky.controller.command.Command;
import by.epam.nickgrudnitsky.entity.dto.PostDTO;
import by.epam.nickgrudnitsky.exception.ParameterValidationException;
import by.epam.nickgrudnitsky.exception.PostServiceException;
import by.epam.nickgrudnitsky.exception.UserServiceException;
import by.epam.nickgrudnitsky.service.impl.PostService;
import by.epam.nickgrudnitsky.service.impl.PostServiceImpl;
import by.epam.nickgrudnitsky.service.impl.UserService;
import by.epam.nickgrudnitsky.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

import static by.epam.nickgrudnitsky.util.HttpUtil.getRequestParameter;

public class MainPageCommand implements Command {
    private static final PostService POST_SERVICE = new PostServiceImpl();
    private static final UserService USER_SERVICE = new UserServiceImpl();

    @Override
    public Action execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Integer from = Integer.parseInt(getRequestParameter(req, "from"));
            Integer to = Integer.parseInt(getRequestParameter(req, "to"));
            loadPostsFromTo(req, from, to);
            req.getSession().setAttribute("nextFrom", to);
            req.getSession().setAttribute("nextTo", to + 5);
            req.getSession().setAttribute("previousFrom", from - 5);
            req.getSession().setAttribute("previousTo", from);
        } catch (ParameterValidationException e) {
            log.error("IN MainPageCommand.execute - failed to validate parameter");
        }
        return Action.MAIN_PAGE;
    }

    private void loadPostsFromTo(HttpServletRequest req, Integer from, Integer to) {
        try {
            List<PostDTO> posts = POST_SERVICE.findFromTo(from, to)
                    .stream()
                    .map(PostDTO::convertToPostDTO)
                    .collect(Collectors.toList());
            for (PostDTO post : posts) {
                post.setUser(USER_SERVICE.findById(post.getUserId()));
            }
            req.getSession().setAttribute("posts", posts);
        } catch (PostServiceException e) {
            log.error(String.format(
                    "IN MainPageCommand.loadPostsFromTo - failed to load post from %s to %s", from, to));
        } catch (UserServiceException e) {
            log.error("IN MainPageCommand.loadPostsFromTo - failed to user by id");
        }
    }
}
