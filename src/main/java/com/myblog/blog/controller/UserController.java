package com.myblog.blog.controller;

import com.myblog.blog.dto.ChangeEmailDto;
import com.myblog.blog.dto.ChangePasswordDto;
import com.myblog.blog.dto.CredentialsDto;
import com.myblog.blog.dto.UserDto;
import com.myblog.blog.service.UserService;
import com.myblog.blog.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/v1/users")
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

    @PutMapping("/update-user-info")
    public User updateUserInfo(@RequestBody UserDto request, Principal connectedUser) {
        return userservice.updateUserInfo(request, connectedUser);
    }

    @DeleteMapping("/deleteAll")
    public void deleteAll() {
        userservice.deleteAll();
    }

    @DeleteMapping("/delete")
    public String delete(@RequestBody CredentialsDto cred) {
        return userservice.delete(cred);
    }

    @PutMapping("/updateCredentials")
    public String updateCredentials(@RequestBody CredentialsDto cred) {
        return userservice.updateCredentials(cred);
    }

    @PatchMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDto request, Principal connectedUser) {
        userservice.changePassword(request, connectedUser);
        return ResponseEntity.accepted().build();
    }

    @PatchMapping("/change-email")
    public ResponseEntity<?> changeEmail(@RequestBody ChangeEmailDto request, Principal connectedUser) {
        return ResponseEntity.accepted().body(userservice.changeEmail(request, connectedUser));
    }

}
