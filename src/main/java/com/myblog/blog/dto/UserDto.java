package com.myblog.blog.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserDto {

    private String login;
    private String name;
    private String surname;
    private String telephone;

}
