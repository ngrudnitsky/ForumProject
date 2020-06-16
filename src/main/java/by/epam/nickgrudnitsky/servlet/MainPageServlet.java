package by.epam.nickgrudnitsky.servlet;

import by.epam.nickgrudnitsky.dto.MainPagePostDTO;
import by.epam.nickgrudnitsky.entity.Post;
import by.epam.nickgrudnitsky.entity.Status;
import by.epam.nickgrudnitsky.entity.User;
import by.epam.nickgrudnitsky.exception.PostServiceException;
import by.epam.nickgrudnitsky.exception.UserServiceException;
import by.epam.nickgrudnitsky.service.PostService;
import by.epam.nickgrudnitsky.service.UserService;
import by.epam.nickgrudnitsky.service.impl.PostServiceImpl;
import by.epam.nickgrudnitsky.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MainPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            PostService postService = new PostServiceImpl();
            UserService userService = new UserServiceImpl();
            List<Post> foundPosts = postService.findAll();
            foundPosts = foundPosts.stream()
                    .filter(p -> p.getStatus().equals(Status.ACTIVE))
                    .collect(Collectors.toList());
            List<MainPagePostDTO> posts = foundPosts.stream()
                    .map(MainPagePostDTO::fromUser)
                    .collect(Collectors.toList());
            for (MainPagePostDTO post : posts) {
                post.setUser(userService.findById(post.getUserId()));
            }
            req.setAttribute("posts", posts);
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        } catch (PostServiceException e) {
            resp.sendRedirect("do?command=error");
        } catch (UserServiceException e) {
            e.printStackTrace();
        }
    }
}
