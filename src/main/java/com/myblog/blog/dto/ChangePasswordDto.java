package com.myblog.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ChangePasswordDto {

    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
