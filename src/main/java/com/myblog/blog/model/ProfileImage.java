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
@Table(name="profile_images", uniqueConstraints = @UniqueConstraint(name = "unique_user", columnNames = "user_id"))
public class ProfileImage {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(nullable = false, unique = true)
    private String imgName;
    @Column(nullable = false)
    private String imgType;
    @Column(nullable = false)
    @Lob
    private byte[] imgData;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;
}
