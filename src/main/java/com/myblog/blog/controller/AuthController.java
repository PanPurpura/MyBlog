package com.myblog.blog.controller;

import com.myblog.blog.dto.AuthenticationResponseDto;
import com.myblog.blog.dto.CredentialsDto;
import com.myblog.blog.dto.UserDto;
import com.myblog.blog.model.User;
import com.myblog.blog.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.OutputKeys;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    @PostMapping("/logged")
    public ResponseEntity<AuthenticationResponseDto> loggingIn(@RequestBody CredentialsDto cred) {
        return ResponseEntity.ok(authService.login(cred));

    }

    @PostMapping("/registration")
    public ResponseEntity<AuthenticationResponseDto> registration(@RequestBody UserDto user) {
        return ResponseEntity.ok(authService.register(user));
    }
}
