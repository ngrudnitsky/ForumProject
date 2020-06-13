package by.epam.nickgrudnitsky.service.impl;

import by.epam.nickgrudnitsky.data.PostRepository;
import by.epam.nickgrudnitsky.data.impl.PostRepositoryImpl;
import by.epam.nickgrudnitsky.entity.Post;
import by.epam.nickgrudnitsky.entity.Status;
import by.epam.nickgrudnitsky.exception.*;
import by.epam.nickgrudnitsky.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

public class PostServiceImpl implements PostService {
    private final PostRepository postRepository = new PostRepositoryImpl();
    private final Logger log = LoggerFactory.getLogger(PostServiceImpl.class);

    @Override
    public Post create(Post post) throws PostServiceException {
        if (post == null) {
            String errorMessage = "IN PostServiceImpl.create  - user is null";
            log.error(errorMessage);
            throw new PostServiceException(errorMessage);
        }
        post.setStatus(Status.ACTIVE);
        post.setCreated(new Date());
        post.setUpdated(new Date());
        Post createdPost;
        try {
            createdPost = postRepository.create(post);
        } catch (PostRepositoryException e) {
            String errorMessage = String.format("IN PostServiceImpl - failed to create post %s", post);
            log.error(errorMessage);
            throw new PostServiceException(errorMessage, e);
        }
        log.info("IN PostServiceImpl.create - post: {} successfully registered", createdPost);
        return createdPost;
    }

    @Override
    public List<Post> findAll() throws PostServiceException {
        List<Post> result;
        try {
            result = postRepository.findAll();
        } catch (PostRepositoryException e) {
            String errorMessage = "IN PostServiceImpl - failed to get all users";
            log.error(errorMessage);
            throw new PostServiceException(errorMessage, e);
        }
        log.info("IN PostServiceImpl.findAll - {} users found", result.size());
        return result;
    }

    @Override
    public Post findById(Integer id) throws PostServiceException {
        Post result;
        if (id == null) {
            throw new PostServiceException("IN PostServiceImpl.findById Null id was found");
        }
        try {
            result = postRepository.findById(id);
        } catch (PostRepositoryException e) {
            String errorMessage = String.format("Failed to find user by id %s", id);
            log.error(errorMessage);
            throw new PostServiceException(errorMessage, e);
        }
        log.info("IN PostServiceImpl.findById - user: {} found by id: {}", result, id);
        return result;
    }

    @Override
    public Post deleteById(Integer id) throws PostServiceException {
        if (id == null) {
            throw new PostServiceException("IN PostServiceImpl.delete Null id was found");
        }
        Post post;
        try {
            post = postRepository.findById(id);
        } catch (PostRepositoryException e) {
            String errorMessage = String.format("Failed to delete user by id %s", id);
            log.error(errorMessage);
            throw new PostServiceException(errorMessage, e);
        }
        post.setStatus(Status.DELETED);
        post.setUpdated(new Date());
        try {
            postRepository.update(post);
        } catch (PostRepositoryException e) {
            String errorMessage = String.format("Failed to delete user by id %s", id);
            log.error(errorMessage);
            throw new PostServiceException(errorMessage, e);
        }
        log.info("IN PostServiceImpl.delete - user with id: {} successfully deleted", id);
        return post;
    }
}
