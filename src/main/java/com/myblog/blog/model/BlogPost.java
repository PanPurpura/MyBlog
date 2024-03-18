package com.myblog.blog.model;


import jakarta.persistence.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.awt.image.BufferedImage;
import java.util.Objects;

@Entity
@Table(name = "blogposts")
public class BlogPost {

    @Id
    @GeneratedValue
    private Integer Id;

    @OneToOne
    private User user;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String text;

    @ElementCollection
    private List<String> comments;

    private byte[] picture;

    @Column(nullable = false)
    private Date date;

    public BlogPost(Integer id, User user, String title, String text, List<String> comments, byte[] picture, Date date) {
        Id = id;
        this.user = user;
        this.title = title;
        this.text = text;
        this.comments = comments;
        this.picture = picture;
        this.date = date;
    }

    public BlogPost() {
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlogPost blogPost = (BlogPost) o;
        return Objects.equals(Id, blogPost.Id) && Objects.equals(user, blogPost.user) && Objects.equals(title, blogPost.title) && Objects.equals(text, blogPost.text) && Objects.equals(comments, blogPost.comments) && Arrays.equals(picture, blogPost.picture) && Objects.equals(date, blogPost.date);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(Id, user, title, text, comments, date);
        result = 31 * result + Arrays.hashCode(picture);
        return result;
    }

    @Override
    public String toString() {
        return "BlogPost{" +
                "Id=" + Id +
                ", user=" + user +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", comments=" + comments +
                ", picture=" + Arrays.toString(picture) +
                ", date=" + date +
                '}';
    }
}
