package com.myblog.blog.dto;

import java.util.Objects;

public class UserPasswordDto {

    private String password;

    public UserPasswordDto(String password) {
        this.password = password;
    }

    public UserPasswordDto() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPasswordDto that = (UserPasswordDto) o;
        return Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password);
    }

    @Override
    public String toString() {
        return "UserPasswordDTO{" +
                "password='" + password + '\'' +
                '}';
    }
}
