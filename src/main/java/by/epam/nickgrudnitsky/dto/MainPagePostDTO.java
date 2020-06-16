package by.epam.nickgrudnitsky.dto;

import by.epam.nickgrudnitsky.entity.Post;
import by.epam.nickgrudnitsky.entity.Status;
import by.epam.nickgrudnitsky.entity.User;

import java.sql.Date;

public class MainPagePostDTO {
    private Integer id;
    private String title;
    private String content;
    private Integer userId;
    private String status;
    private Date created;
    private Date updated;
    private User user;

    public static MainPagePostDTO fromUser(Post post) {
        MainPagePostDTO mainPagePostDTO = new MainPagePostDTO();
        mainPagePostDTO.setId(post.getId());
        mainPagePostDTO.setTitle(post.getTitle());
        mainPagePostDTO.setContent(post.getContent());
        mainPagePostDTO.setUserId(post.getUserId());
        mainPagePostDTO.setStatus(post.getStatus().name());
        mainPagePostDTO.setCreated(new Date(post.getCreated().getTime()));
        mainPagePostDTO.setUpdated(new Date(post.getUpdated().getTime()));
        return mainPagePostDTO;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
