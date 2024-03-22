package com.myblog.blog.controller;

import com.myblog.blog.DTO.UserDTO;
import com.myblog.blog.Services.UserService;
import com.myblog.blog.model.User;
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
    public String loggingIn(@RequestBody Credentials cred) {
        return userservice.login(cred.email(), cred.password());
    }

    @PostMapping("/registration")
    public String registration(@RequestBody User user) {
        return userservice.register(user);
    }

    @PutMapping("/update")
    public String update(@RequestBody UserDTO user) {
        return userservice.updateUser(user);
    }

    @DeleteMapping("/deleteAll")
    public void deleteAll() {
        userservice.deleteAll();
    }

    @DeleteMapping("/delete")
    public String delete(@RequestBody Credentials cred) {
        return userservice.delete(cred.email(), cred.password());
    }

}
