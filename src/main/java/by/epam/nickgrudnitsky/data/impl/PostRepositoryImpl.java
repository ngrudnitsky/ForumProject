package by.epam.nickgrudnitsky.data.impl;

import by.epam.nickgrudnitsky.data.PostRepository;
import by.epam.nickgrudnitsky.entity.Post;
import by.epam.nickgrudnitsky.entity.Status;
import by.epam.nickgrudnitsky.exception.PostRepositoryException;
import by.epam.nickgrudnitsky.util.JdbcConnection;
import org.apache.commons.lang3.EnumUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostRepositoryImpl implements PostRepository {
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM posts WHERE id = '%s'";
    private static final String SAVE_POST_QUERY = "INSERT INTO posts(title, content, status," +
            "createdAt, updatedAt, users_id)VALUES(?,?,?,?,?,?)";
    private static final String FIND_ALL_POSTS_QUERY = "SELECT id FROM posts";
    private static final String UPDATE_POST_QUERY = "UPDATE posts SET title = ?, content = ?, status = ?, " +
            "updatedAt = ? WHERE id = ?";

    private final Connection connection = JdbcConnection.getConnection();

    @Override
    public Post findById(Integer id) throws PostRepositoryException {
        String ERROR_MESSAGE = "IN PostRepositoryImpl failed to find post id %d";
        try {
            Post post = new Post();
            ResultSet resultSet = connection.createStatement().executeQuery(String.format(FIND_BY_ID_QUERY, id));
            if (resultSet.next()) {
                post.setId(id);
                post.setTitle(resultSet.getString("title"));
                post.setContent(resultSet.getString("content"));
                post.setCreated(resultSet.getDate("createdAt"));
                post.setUpdated(resultSet.getDate("updatedAt"));
                post.setStatus(EnumUtils.getEnum(Status.class, resultSet.getString("status")));
                return post;
            }
        } catch (SQLException e) {
            throw new PostRepositoryException(
                    String.format(ERROR_MESSAGE, id), e);
        }
        throw new PostRepositoryException(
                String.format(ERROR_MESSAGE, id));
    }

    @Override
    public Post update(Post post) throws PostRepositoryException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_POST_QUERY);
            preparedStatement.setString(1, post.getTitle());
            preparedStatement.setString(2, post.getContent());
            preparedStatement.setString(3, post.getStatus().name());
            preparedStatement.setDate(4, new Date(post.getCreated().getTime()));
            preparedStatement.setInt(5, post.getId());
            preparedStatement.executeUpdate();
            return post;
        } catch (SQLException e) {
            throw new PostRepositoryException(
                    String.format("IN PostRepositoryImpl failed to update post %s", post), e);
        }
    }

    @Override
    public Post save(Post post) throws PostRepositoryException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SAVE_POST_QUERY);
            preparedStatement.setString(1, post.getTitle());
            preparedStatement.setString(2, post.getContent());
            preparedStatement.setString(3, post.getStatus().name());
            preparedStatement.setDate(4, new Date(post.getCreated().getTime()));
            preparedStatement.setDate(5, new Date(post.getCreated().getTime()));
            preparedStatement.setInt(6, post.getUserId());
            preparedStatement.executeUpdate();
            return post;
        } catch (SQLException e) {
            throw new PostRepositoryException(
                    String.format("IN PostRepositoryImpl failed to save post %s", post), e);
        }
    }

    @Override
    public List<Post> findAll() throws PostRepositoryException {
        try {
            List<Post> users = new ArrayList<>();
            ResultSet resultSet = connection.createStatement().executeQuery(FIND_ALL_POSTS_QUERY);
            while (resultSet.next()) {
                users.add(findById(resultSet.getInt(1)));
            }
            return users;
        } catch (SQLException e) {
            throw new PostRepositoryException("IN PostRepositoryImpl failed to find all posts.", e);
        }
    }
}
