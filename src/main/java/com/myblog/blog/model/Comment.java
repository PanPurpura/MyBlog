package com.myblog.blog.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue
    private Integer id;
    @Column(nullable = false)
    private LocalDateTime addedAt;
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    @Column(nullable = false)
    private String text;
    @ManyToOne
    @JoinColumn(name = "blog_post_id")
    private BlogPost blogPost;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
