package by.epam.nickgrudnitsky.servlet;

import by.epam.nickgrudnitsky.entity.Post;
import by.epam.nickgrudnitsky.exception.PostServiceException;
import by.epam.nickgrudnitsky.service.PostService;
import by.epam.nickgrudnitsky.service.impl.PostServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class MainPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        PostService postService = new PostServiceImpl();
        try {
            List<Post> posts = postService.findAll();
            req.setAttribute("posts", posts);
        } catch (PostServiceException e) {
            resp.sendRedirect("do?command=error");
        }
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}
