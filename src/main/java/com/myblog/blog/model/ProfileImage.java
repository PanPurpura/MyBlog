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
@Table(name="profile_images")
public class ProfileImage {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(nullable = false)
    private String imgName;
    @Column(nullable = false)
    private String imgType;
    @Column(nullable = false)
    private byte[] imgData;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
