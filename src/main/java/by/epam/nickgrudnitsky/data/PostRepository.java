package by.epam.nickgrudnitsky.data;

import by.epam.nickgrudnitsky.entity.Post;
import by.epam.nickgrudnitsky.exception.PostRepositoryException;

import java.util.List;

public interface PostRepository {
    Post findById(Integer id) throws PostRepositoryException;

    Post update(Post post) throws PostRepositoryException;

    Post deleteById(Integer id) throws PostRepositoryException;

    Post create(Post post) throws PostRepositoryException;

    List<Post> findAll() throws PostRepositoryException;

    List<Post> findFromTo(Integer from, Integer to) throws PostRepositoryException;

    Integer getLastId() throws PostRepositoryException;
}
