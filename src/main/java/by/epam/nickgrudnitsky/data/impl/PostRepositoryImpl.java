package by.epam.nickgrudnitsky.data.impl;

import by.epam.nickgrudnitsky.data.PostRepository;
import by.epam.nickgrudnitsky.entity.Post;
import by.epam.nickgrudnitsky.entity.Status;
import by.epam.nickgrudnitsky.exception.PostRepositoryException;
import by.epam.nickgrudnitsky.util.JdbcConnection;
import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostRepositoryImpl implements PostRepository {
    private final Logger log = LoggerFactory.getLogger(PostRepositoryImpl.class);
    private final Connection connection = JdbcConnection.getConnection();

    @Override
    public Post findById(Integer id) throws PostRepositoryException {
        String findByIdQuery = String.format("SELECT * FROM posts WHERE id = '%s'", id);
        String errorMessage = String.format("IN PostRepositoryImpl.findById failed to find post by id %d", id);
        try {
            ResultSet resultSet = connection.createStatement().executeQuery(findByIdQuery);
            Post post = new Post();
            if (resultSet.next()) {
                post.setId(id);
                post.setTitle(resultSet.getString("title"));
                post.setContent(resultSet.getString("content"));
                post.setCreated(resultSet.getDate("createdAt"));
                post.setUpdated(resultSet.getDate("updatedAt"));
                post.setStatus(EnumUtils.getEnum(Status.class, resultSet.getString("status")));
                post.setUserId(resultSet.getInt("users_id"));
                return post;
            }
        } catch (SQLException e) {
            log.error(errorMessage);
            throw new PostRepositoryException(errorMessage, e);
        }
        log.error(errorMessage);
        throw new PostRepositoryException(errorMessage);
    }

    @Override
    public Post update(Post post) throws PostRepositoryException {
        try {
            String updatePostQuery = "UPDATE posts SET title = ?, content = ?, status = ?, " +
                    "updatedAt= ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updatePostQuery);
            preparedStatement.setString(1, post.getTitle());
            preparedStatement.setString(2, post.getContent());
            preparedStatement.setString(3, post.getStatus().name());
            preparedStatement.setDate(4, new Date(post.getUpdated().getTime()));
            preparedStatement.setInt(5, post.getId());
            preparedStatement.executeUpdate();
            return post;
        } catch (SQLException e) {
            String errorMessage = String.format(
                    "IN PostRepositoryImpl.update failed to update post with id %d", post.getId());
            log.error(errorMessage);
            throw new PostRepositoryException(errorMessage, e);
        }
    }

    @Override
    public Post deleteById(Integer id) throws PostRepositoryException {
        try {
            String updateUserQuery = "UPDATE posts SET status = ?, updatedAt = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateUserQuery);
            preparedStatement.setString(1, Status.DELETED.name());
            preparedStatement.setDate(2, new Date(System.currentTimeMillis()));
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
            return findById(id);
        } catch (SQLException e) {
            String errorMessage = String.format(
                    "IN PostRepositoryImpl.deleteById failed to delete post with id %s", id);
            log.error(errorMessage);
            throw new PostRepositoryException(errorMessage, e);
        }
    }

    @Override
    public Post create(Post post) throws PostRepositoryException {
        try {
            String createPostQuery = "INSERT INTO posts(title, content, status," +
                    "createdAt, updatedAt, users_id)VALUES(?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(createPostQuery);
            preparedStatement.setString(1, post.getTitle());
            preparedStatement.setString(2, post.getContent());
            preparedStatement.setString(3, post.getStatus().name());
            preparedStatement.setDate(4, new Date(post.getCreated().getTime()));
            preparedStatement.setDate(5, new Date(post.getCreated().getTime()));
            preparedStatement.setInt(6, post.getUserId());
            preparedStatement.executeUpdate();
            return post;
        } catch (SQLException e) {
            String errorMessage = String.format(
                    "IN PostRepositoryImpl.create failed to create new post %s", post.getTitle());
            log.error(errorMessage);
            throw new PostRepositoryException(errorMessage, e);
        }
    }

    //todo findFromTo
    @Override
    public List<Post> findAll() throws PostRepositoryException {
        try {
            String findAllPostsQuery = "SELECT id FROM posts";
            List<Post> posts = new ArrayList<>();
            ResultSet resultSet = connection.createStatement().executeQuery(findAllPostsQuery);
            while (resultSet.next()) {
                posts.add(findById(resultSet.getInt(1)));
            }
            return posts;
        } catch (SQLException e) {
            String errorMessage = "IN PostRepositoryImpl.findAll failed to find all posts";
            log.error(errorMessage);
            throw new PostRepositoryException(errorMessage, e);
        }
    }
}
