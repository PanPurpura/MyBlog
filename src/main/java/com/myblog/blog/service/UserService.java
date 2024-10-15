package com.myblog.blog.service;

import com.myblog.blog.dto.*;
import com.myblog.blog.exception.InvalidCredentialsException;
import com.myblog.blog.exception.NotFoundException;
import com.myblog.blog.mapper.CredentialsMapper;
import com.myblog.blog.mapper.UserMapper;
import com.myblog.blog.model.User;
import com.myblog.blog.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

import static com.myblog.blog.constant.ApplicationConstant.NO_USER_FOUND;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper userMapper;
    private final CredentialsMapper credentialsMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public List<User> getAll() {
        return (List<User>) repository.findAll();
    }

    public User updateUserInfo(UserDto dto, Integer userId) {

        if(userId == null)
            throw new NotFoundException("Wrong userId");

        User myUser = repository.findById(userId).orElseThrow(() -> new NotFoundException(NO_USER_FOUND));
        User u = userMapper.updateUserFromDto(dto, myUser);
        repository.save(u);
        return u;
    }

    public String delete(CredentialsDto dto) {
        User myUser = repository.findByEmailAndPassword(dto.getEmail(), dto.getPassword());
        repository.delete(myUser);
        return "Deleted successfully";
    }

    //Only for testing purposes
    public void deleteAll() {
        repository.deleteAll();
    }

    //To delete
    public String updateCredentials(CredentialsDto dto) {
        User myUser = repository.findByEmail(dto.getEmail()).orElseThrow(() -> new NotFoundException(NO_USER_FOUND));
        credentialsMapper.updateUserFromCredentialsDto(dto, myUser);
        repository.save(myUser);
        return "Credentials Updated";
    }

    public void changePassword(ChangePasswordDto request, Integer userId) {

        var user = repository.findById(userId).orElseThrow(() -> new NotFoundException(NO_USER_FOUND));

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Wrong password!");
        }

        if(passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("New password and the old one are the same!");
        }

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new InvalidCredentialsException("Passwords are not the same!");
        }

        String newPassword = passwordEncoder.encode(request.getNewPassword());
        user.setPassword(newPassword);
        repository.save(user);

    }

    public AuthenticationResponseDto changeEmail(ChangeEmailDto request, Integer userId) {

        var user = repository.findById(userId).orElseThrow(() -> new NotFoundException(NO_USER_FOUND));

        if (!passwordEncoder.matches(request.getConfirmPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Wrong password!");
        }

        if(passwordEncoder.matches(request.getNewEmail(), user.getEmail())) {
            throw new InvalidCredentialsException("New email and the old one are the same!");
        }

        user.setEmail(request.getNewEmail());
        repository.save(user);

        Map<String, Object> claims = Map.of(
                "id", user.getId(),
                "login", user.getLogin(),
                "name", user.getName(),
                "surname", user.getSurname(),
                "telephone", user.getTelephone(),
                "role", user.getRole()
        );

        var jwtToken = jwtService.generateToken(claims, user);

        return new AuthenticationResponseDto(jwtToken);

    }

    public void saveUser(User user) {
        repository.save(user);
    }

}
