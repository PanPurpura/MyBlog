package com.myblog.blog.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myblog.blog.dto.CredentialsDto;
import com.myblog.blog.dto.UserDto;
import com.myblog.blog.mapper.CredentialsMapper;
import com.myblog.blog.mapper.UserMapper;
import com.myblog.blog.model.User;
import com.myblog.blog.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper userMapper;
    private final CredentialsMapper credentialsMapper;

    public List<User> getAll() {
        return (List<User>) repository.findAll();
    }

    public List<User> getActive() {
        return (List<User>) repository.findAllByActive(Boolean.TRUE);
    }

    public String login(CredentialsDto dto) {
        User found = repository.findByEmailAndPassword(dto.getEmail(), dto.getPassword());

        if (found != null)
            return "Logged in";
        else
            return "Wrong email or password";
    }

    public String register(User user) {

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
            u.setName(user.getName());
            u.setSurname(user.getSurname());
            u.setActive(Boolean.TRUE);
            u.setLocked(Boolean.FALSE);
            u.setRole(user.getRole());
            repository.save(u);
            return "Account successfully created";
        }

    }

    public String updateUser(UserDto dto) {
        User myUser = repository.findByEmail(dto.getEmail());
        userMapper.updateUserFromDto(dto, myUser);
        repository.save(myUser);
        return "User data updated";
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
        User myUser = repository.findByEmail(dto.getEmail());
        credentialsMapper.updateUserFromCredentialsDto(dto, myUser);
        repository.save(myUser);
        return "Credentials Updated";
    }

}
