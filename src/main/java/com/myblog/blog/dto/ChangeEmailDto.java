package com.myblog.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
@AllArgsConstructor
public class ChangeEmailDto {

    @NonNull
    private String newEmail;
    @NonNull
    private String confirmPassword;

}
