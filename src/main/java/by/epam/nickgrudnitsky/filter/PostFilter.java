package by.epam.nickgrudnitsky.filter;

import by.epam.nickgrudnitsky.entity.Status;
import by.epam.nickgrudnitsky.entity.dto.PostDTO;
import by.epam.nickgrudnitsky.exception.PostServiceException;
import by.epam.nickgrudnitsky.exception.UserServiceException;
import by.epam.nickgrudnitsky.service.PostService;
import by.epam.nickgrudnitsky.service.UserService;
import by.epam.nickgrudnitsky.service.impl.PostServiceImpl;
import by.epam.nickgrudnitsky.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class PostFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Logger log = LoggerFactory.getLogger(PostFilter.class);
        try {
            HttpServletRequest req = (HttpServletRequest) servletRequest;
            PostService postService = new PostServiceImpl();
            UserService userService = new UserServiceImpl();
            List<PostDTO> posts = postService.findAll()
                    .stream()
                    .filter(p -> p.getStatus().equals(Status.ACTIVE))
                    .map(PostDTO::convertToPostDTO)
                    .collect(Collectors.toList());
            for (PostDTO post : posts) {
                post.setUser(userService.findById(post.getUserId()));
            }
            req.setAttribute("posts", posts);
            filterChain.doFilter(req, servletResponse);
        } catch (UserServiceException e) {
            log.error("IN PostFilter.doFilter - failed to find user by id");
        } catch (PostServiceException e) {
            log.error("IN PostFilter.doFilter - failed to find all posts");
        }
    }

    @Override
    public void destroy() {
    }
}
