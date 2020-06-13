package by.epam.nickgrudnitsky.entity;

import java.util.Objects;

public class Post extends BaseEntity {
    private Integer id;
    private String title;
    private String content;
    private Integer userId;

    @Override
    public String toString() {
        return "Post " + title + "\n" + content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Post post = (Post) o;

        if (!Objects.equals(id, post.id)) return false;
        if (!Objects.equals(title, post.title)) return false;
        if (!Objects.equals(content, post.content)) return false;
        return Objects.equals(userId, post.userId);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        return result;
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
}
