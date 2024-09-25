package com.myblog.blog.service;

import com.myblog.blog.dto.ChangePasswordDto;
import com.myblog.blog.dto.CredentialsDto;
import com.myblog.blog.dto.UserDto;
import com.myblog.blog.exception.InvalidPasswordException;
import com.myblog.blog.mapper.CredentialsMapper;
import com.myblog.blog.mapper.UserMapper;
import com.myblog.blog.model.User;
import com.myblog.blog.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper userMapper;
    private final CredentialsMapper credentialsMapper;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAll() {
        return (List<User>) repository.findAll();
    }

    public User updateUserInfo(UserDto dto, Principal connectedUser) {
        var myUser = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        userMapper.updateUserFromDto(dto, myUser);
        repository.save(myUser);
        return myUser;
    }

    public String delete(CredentialsDto dto) {
        User myUser = repository.findByEmailAndPassword(dto.getEmail(), dto.getPassword());
        repository.delete(myUser);
        return "Deleted successfully";
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public String updateCredentials(CredentialsDto dto) {
        User myUser = repository.findByEmail(dto.getEmail()).get();
        credentialsMapper.updateUserFromCredentialsDto(dto, myUser);
        repository.save(myUser);
        return "Credentials Updated";
    }

    public void changePassword(ChangePasswordDto request, Principal connectedUser) {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Wrong password!");
        }

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new InvalidPasswordException("Passwords are not the same!");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        repository.save(user);

    }

}
