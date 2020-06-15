package by.epam.nickgrudnitsky.servlet;

import by.epam.nickgrudnitsky.entity.Post;
import by.epam.nickgrudnitsky.entity.Status;
import by.epam.nickgrudnitsky.exception.PostServiceException;
import by.epam.nickgrudnitsky.service.PostService;
import by.epam.nickgrudnitsky.service.impl.PostServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class MainPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            PostService postService = new PostServiceImpl();
            List<Post> posts = postService.findAll();
            posts = posts.stream()
                    .filter(p -> p.getStatus().equals(Status.ACTIVE))
                    .collect(Collectors.toList());
            req.setAttribute("posts", posts);
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        } catch (PostServiceException e) {
            resp.sendRedirect("do?command=error");
        }
    }
}
