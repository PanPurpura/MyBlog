package com.myblog.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@Builder
@AllArgsConstructor
public class ChangePasswordDto {

    @NonNull
    private String oldPassword;
    @NonNull
    private String newPassword;
    @NonNull
    private String confirmPassword;
}
