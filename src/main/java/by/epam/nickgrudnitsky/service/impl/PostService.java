package by.epam.nickgrudnitsky.service.impl;

import by.epam.nickgrudnitsky.entity.Post;
import by.epam.nickgrudnitsky.exception.PostServiceException;

import java.util.List;

public interface PostService {
    Post create(Post post) throws PostServiceException;

    Post updatePost(Post post) throws PostServiceException;

    List<Post> findAll() throws PostServiceException;

    List<Post> findFromTo(Integer from, Integer to) throws PostServiceException;

    Post findById(Integer id) throws PostServiceException;

    Post deleteById(Integer id) throws PostServiceException;

    Integer getLastId() throws PostServiceException;
}
