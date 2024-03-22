package com.myblog.blog.service;

import com.myblog.blog.dto.UserDto;
import com.myblog.blog.mapper.UserMapper;
import com.myblog.blog.model.User;
import com.myblog.blog.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
  /*  public enum roles {
        MODERATOR,
        ADMIN,
        USER,
        VISITATOR
    }*/

    public UserService(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<User> getAll() {
        return (List<User>) repository.findAll();
    }

    public List<User> getActive() {
        return (List<User>) repository.findAllByActive(Boolean.TRUE);
    }

    public String login(String email, String password) {
        User found = repository.findByEmailAndPassword(email, password);

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
        mapper.updateUserFromDto(dto, myUser);
        repository.save(myUser);
        return "User data updated";
    }

    public String delete(String email, String password) {
        User myUser = repository.findByEmailAndPassword(email, password);
        repository.delete(myUser);
        return "Deleted successfully";
    }

    public void deleteAll() {
        repository.deleteAll();
    }




}
