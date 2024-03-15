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

    public record Credentials(String email, String password) {};

    @GetMapping("/all")
    public List<User> getAll() {
        return (List<User>) repository.findAll();
    }

    @PostMapping("/getLogged")
    public String getLogged(@RequestBody Credentials cred) {
        Credentials c = new Credentials(cred.email(), cred.password());
        User logged = repository.getUserByEmailAAndPassword(c.email(), c.password());

        if(logged == null)
            return "Wrong email or password";
        else
            return "Successfully logged";

    }
    @PostMapping("/registration")
    public String registration(@RequestBody User user) {

        if(repository.getUserByEmail(user.getEmail()) != null) {
            return "Email already taken";
        }
        else if (repository.getUserByUsername(user.getUsername()) != null) {
            return  "Username already taken";
        }
        else {
            User u = new User();
            u.setUsername(user.getUsername());
            u.setEmail(user.getEmail());
            u.setPassword(user.getPassword());
            repository.save(u);
            return "Account successfully created!";
        }


    }

}
