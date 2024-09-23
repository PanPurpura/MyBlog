package com.myblog.blog.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="blog_post_images")
public class BlogPostImage {

    @Id
    @GeneratedValue
    private Integer id;
    @Column(nullable = false)
    private String imgName;
    @Column(nullable = false)
    private String imgType;
    @Column(nullable = false)
    private byte[] imgData;

    @ManyToOne
    @JoinColumn(name = "blog_post_id")
    private BlogPost blogPost;
}
