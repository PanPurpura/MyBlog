package com.myblog.blog.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.awt.image.BufferedImage;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "blog_posts")
public class BlogPost {

    @Id
    @GeneratedValue
    private Integer Id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String text;

    @OneToMany(mappedBy = "blogPost")
    private List<Comment> commentList;

    @OneToMany(mappedBy = "blogPost")
    private List<BlogPostImage> blogPostImageList;

    @Column(nullable = false)
    private LocalDateTime addedAt;
    @Column(nullable = false)
    private LocalDateTime updatedAt;

}
