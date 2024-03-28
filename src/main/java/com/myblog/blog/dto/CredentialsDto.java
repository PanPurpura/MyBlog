package com.myblog.blog.dto;

public class CredentialsDto {

    private String password;
    private String email;

    public CredentialsDto(String password, String email) {
        this.password = password;
        this.email = email;
    }

    public CredentialsDto() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "PasswordDto{" +
                "password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
