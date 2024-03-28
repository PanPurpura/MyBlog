package com.myblog.blog.controller;

import com.myblog.blog.dto.CredentialsDto;
import com.myblog.blog.dto.UserDto;
import com.myblog.blog.service.UserService;
import com.myblog.blog.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class UserController {

    private final UserService userservice;

    public UserController(UserService userservice) {
        this.userservice = userservice;
    }

    @GetMapping("/all")
    public List<User> All() {
        return userservice.getAll();
    }

    record Credentials(String email, String password) {};

    @PostMapping("/logged")
    public String loggingIn(@RequestBody CredentialsDto cred) {
        return userservice.login(cred);
    }

    @PostMapping("/registration")
    public String registration(@RequestBody User user) {
        return userservice.register(user);
    }

    @PutMapping("/update")
    public String update(@RequestBody UserDto user) {
        return userservice.updateUser(user);
    }

    @DeleteMapping("/deleteAll")
    public void deleteAll() {
        userservice.deleteAll();
    }

    @DeleteMapping("/delete")
    public String delete(@RequestBody CredentialsDto cred) {
        return userservice.delete(cred);
    }

    @PutMapping("updateCredentials")
    public String updateCredentials(@RequestBody CredentialsDto cred) {
        return userservice.updateCredentials(cred);
    }

}
