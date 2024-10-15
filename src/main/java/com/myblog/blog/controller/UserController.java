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

    @PutMapping("/update-user-info/{userId}")
    public User updateUserInfo(@RequestBody UserDto request, @PathVariable Integer userId) {
        return userservice.updateUserInfo(request, userId);
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

    @PatchMapping("/change-password/{userId}")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDto request, @PathVariable Integer userId) {
        userservice.changePassword(request, userId);
        return ResponseEntity.accepted().build();
    }

    @PatchMapping("/change-email/{userId}")
    public ResponseEntity<?> changeEmail(@RequestBody ChangeEmailDto request, @PathVariable Integer userId) {
        return ResponseEntity.accepted().body(userservice.changeEmail(request, userId));
    }

}
