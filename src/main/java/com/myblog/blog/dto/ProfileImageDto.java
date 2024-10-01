package com.myblog.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProfileImageDto {
    private byte[] imageData;
    private String imageType;
    private Integer userId;
}
