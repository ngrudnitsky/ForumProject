package by.epam.nickgrudnitsky.data.impl;

import by.epam.nickgrudnitsky.entity.Post;
import by.epam.nickgrudnitsky.entity.Status;
import by.epam.nickgrudnitsky.entity.User;
import by.epam.nickgrudnitsky.exception.PostRepositoryException;
import by.epam.nickgrudnitsky.exception.UserRepositoryException;
import by.epam.nickgrudnitsky.util.JdbcConnection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PostRepositoryImplTest {
    private PostRepositoryImpl postRepository;
    private Post post;

    @BeforeEach
    void initialize() {
        postRepository = new PostRepositoryImpl();
        post = new Post();
        post.setId(-1);
        post.setTitle("test title");
        post.setContent("test content");
        post.setStatus(Status.ACTIVE);
        post.setCreated(new Date());
        post.setUpdated(new Date());
        post.setUserId(-1);
    }

    @Test
    void findById() throws PostRepositoryException {
        int id = 1;
        Post post = postRepository.findById(id);
        assertEquals(post.getId(), id);
    }

    @Test
    void findByIdWithWrongId() {
        assertThrows(PostRepositoryException.class, () ->
                postRepository.findById(Integer.MAX_VALUE));
    }

    @Test
    void update() throws PostRepositoryException {
        Post savesUser = postRepository.update(post);
        assertNotNull(savesUser);
    }

    @Test
    void save() throws PostRepositoryException {
        Post savedPost = postRepository.create(post);
        assertNotNull(savedPost);
    }

    @Test
    void findAll() throws PostRepositoryException {
        List<Post> posts = postRepository.findAll();
        assertNotNull(posts);
    }

    @Test
    void deleteUserById() throws  PostRepositoryException {
        int id = 1;
        Post post = postRepository.deleteById(id);
        assertEquals(Status.DELETED, post.getStatus());
    }

    @Test
    void deleteUserByIdWithWrongId() {
        assertThrows(PostRepositoryException.class, () ->
                postRepository.deleteById(Integer.MAX_VALUE));
    }

    @Test
    void getLastId() throws PostRepositoryException {
        post = postRepository.create(post);
        Integer lastId = postRepository.getLastId();
        assertNotNull(lastId);
    }

    @AfterAll
    static void resetDataBase() throws SQLException {
        JdbcConnection.reset();
    }
}