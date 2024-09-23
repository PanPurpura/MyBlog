package com.myblog.blog.service;

import com.myblog.blog.dto.AuthenticationResponseDto;
import com.myblog.blog.dto.CredentialsDto;
import com.myblog.blog.dto.UserDto;
import com.myblog.blog.model.Role;
import com.myblog.blog.model.User;
import com.myblog.blog.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    public AuthenticationResponseDto login(CredentialsDto dto) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getEmail(),
                        dto.getPassword()
                )
        );

        var user = repository.findByEmail(dto.getEmail()).orElse(null);

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponseDto.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponseDto register(User request) {

        if (repository.findByEmail(request.getEmail()).orElse(null) != null) {
            return new AuthenticationResponseDto("Email already taken");
        }
        if (repository.findByLogin(request.getLogin()).orElse(null) != null) {
            return new AuthenticationResponseDto("Login already taken");
        }

            var user = User.builder()
                    .name(request.getName())
                    .surname(request.getSurname())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.ADMIN)
                    .telephone(request.getTelephone())
                    .login(request.getLogin())
                    .build();

            repository.save(user);

            Map<String, Object> claims = Map.of(
                    "login", user.getLogin(),
                    "name", user.getName(),
                    "surname", user.getSurname(),
                    "telephone", user.getTelephone(),
                    "role", user.getRole()
            );

            var jwtToken = jwtService.generateToken(claims, user);
            return AuthenticationResponseDto.builder()
                    .token(jwtToken)
                    .build();

        }

}
