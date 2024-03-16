package com.myblog.blog.controller;

import com.myblog.blog.model.User;
import com.myblog.blog.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

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
        return (List<User>) repository.findAll();
    }

    record Credentials(String email, String password) {};

    @PostMapping("/logged")
    public String loggingIn(@RequestBody Credentials cred) {
        User found = repository.findByEmailAndPassword(cred.email(), cred.password());

        if (found != null)
            return "Logged in";
        else
            return "Wrong email or password";
    }

    @PostMapping("/registration")
    public String registration(@RequestBody User user) {

        if(repository.findByEmail(user.getEmail()) != null) {
            return "Email already taken";
        }
        else if(repository.findByUsername(user.getUsername()) != null) {
            return "Username already taken";
        }
        else {
            User u = new User();
            u.setEmail(user.getEmail());
            u.setPassword(user.getPassword());
            u.setUsername(user.getUsername());
            repository.save(u);
            return "Account successfully created";
        }

    }

}
