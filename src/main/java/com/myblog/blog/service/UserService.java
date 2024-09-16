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

    public String updateUser(UserDto dto) {
        User myUser = repository.findByEmail(dto.getEmail()).get();
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
        User myUser = repository.findByEmail(dto.getEmail()).get();
        credentialsMapper.updateUserFromCredentialsDto(dto, myUser);
        repository.save(myUser);
        return "Credentials Updated";
    }

}
