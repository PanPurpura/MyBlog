package com.myblog.blog.DTO;

import java.util.Objects;

public class UserPasswordDTO {

    private String password;

    public UserPasswordDTO(String password) {
        this.password = password;
    }

    public UserPasswordDTO() {
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
        UserPasswordDTO that = (UserPasswordDTO) o;
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
