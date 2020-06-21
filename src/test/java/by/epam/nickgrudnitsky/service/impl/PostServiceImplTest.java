package by.epam.nickgrudnitsky.service.impl;

import by.epam.nickgrudnitsky.entity.Post;
import by.epam.nickgrudnitsky.entity.Status;
import by.epam.nickgrudnitsky.exception.PostServiceException;
import by.epam.nickgrudnitsky.util.JdbcConnection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PostServiceImplTest {
    private PostServiceImpl postService;
    private Post post;

    @BeforeEach
    void initialize() {
        postService = new PostServiceImpl();
        post = new Post();
        post.setId(1);
        post.setTitle("test title");
        post.setPreview("test preview");
        post.setContent("test content");
        post.setUserId(1);
        post.setStatus(Status.ACTIVE);
        post.setCreated(new Date());
        post.setUpdated(new Date());
    }

    @Test
    void createPost() throws PostServiceException {
        Post createdPost = postService.create(post);
        assertEquals(Status.ACTIVE, createdPost.getStatus());
    }

    @Test
    void createPostWithNull() {
        assertThrows(PostServiceException.class, () ->
                postService.create(null));
    }

    @Test
    void findAll() throws PostServiceException {
        List<Post> posts = postService.findAll();
        assertNotNull(posts);
    }

    @Test
    void findById() throws PostServiceException {
        Post foundPost = postService.findById(1);
        assertNotNull(foundPost);
    }

    @Test
    void findByIdWithNullId()  {
        assertThrows(PostServiceException.class, () ->
                postService.findById(null));
    }

    @Test
    void findByIdWithWrongId()  {
        assertThrows(PostServiceException.class, () ->
                postService.findById(-1));
    }

    @Test
    void deleteById() throws PostServiceException {
        Post deletedPost = postService.deleteById(1);
        assertEquals(Status.DELETED, deletedPost.getStatus());
    }

    @Test
    void deleteByIdWithNullId() {
        assertThrows(PostServiceException.class, () ->
                postService.deleteById(null));
    }

    @Test
    void deleteByIdWithWrongId() {
        assertThrows(PostServiceException.class, () ->
                postService.deleteById(-1));
    }

    @Test
    void updatePost() throws PostServiceException {
        String updatedTitle = "Updated Title";
        post.setTitle(updatedTitle);
        Post updatePost = postService.updatePost(post);
        assertEquals(updatedTitle, updatePost.getTitle());

    }

    @Test
    void updatePostWithNullId() {
        assertThrows(PostServiceException.class, () ->
                postService.updatePost(null));
    }

    @AfterAll
    static void resetDataBase() throws SQLException {
        JdbcConnection.reset();
    }

    @Test
    void findFromTo() throws PostServiceException {
        List<Post> posts = postService.findFromTo(1, 5);
        assertEquals(4, posts.size());
    }
}