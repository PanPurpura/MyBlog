package com.myblog.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ChangeEmailDto {

    private String newEmail;
    private String confirmPassword;

}
