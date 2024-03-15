package com.myblog.blog.controller;

import com.myblog.blog.model.User;
import com.myblog.blog.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class UserController {

    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/all")
    public List<User> getAll() {
        System.out.println("SIUU");
        return (List<User>) repository.findAll();
    }

}
