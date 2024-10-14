package com.myblog.blog.service;

import com.myblog.blog.dto.AuthenticationResponseDto;
import com.myblog.blog.dto.ChangeEmailDto;
import com.myblog.blog.dto.ChangePasswordDto;
import com.myblog.blog.dto.UserDto;
import com.myblog.blog.exception.InvalidCredentialsException;
import com.myblog.blog.exception.NotFoundException;
import com.myblog.blog.mapper.CredentialsMapper;
import com.myblog.blog.mapper.UserMapper;
import com.myblog.blog.model.Role;
import com.myblog.blog.model.User;
import com.myblog.blog.repository.UserRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.myblog.blog.constant.ApplicationConstant.NO_USER_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {


    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository repository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private CredentialsMapper credentialsMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);

        userService = new UserService(repository, userMapper, credentialsMapper, passwordEncoder, jwtService);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void updateUserInfoWithCorrectDtoAndUserIdReturnsUpdatedUser() {

        Integer userId = 1;

        UserDto userDto = UserDto.builder()
                .login("PatMoore")
                .name("Patrick")
                .surname("Moore")
                .telephone("987-654-321")
                .build();

        User existedUser = User.builder()
                .surname("Moore1")
                .name("Patrick1")
                .telephone("123-456-789")
                .login("PatMoore1")
                .build();

        User updatedUser = User.builder()
                .surname("Moore")
                .name("Patrick")
                .telephone("987-654-321")
                .login("PatMoore")
                .build();

        when(repository.findById(userId)).thenReturn(Optional.of(existedUser));
        when(userMapper.updateUserFromDto(userDto, Optional.of(existedUser).get())).thenReturn(updatedUser);
        when(repository.save(updatedUser)).thenReturn(updatedUser);

        User result = userService.updateUserInfo(userDto, userId);

        verify(repository, times(1)).findById(userId);
        verify(userMapper, times(1)).updateUserFromDto(userDto, existedUser);
        verify(repository, times(1)).save(updatedUser);

        assertThat(result).isNotNull();
        assertThat(result.getLogin()).isEqualTo("PatMoore");
        assertThat(result.getName()).isEqualTo("Patrick");
        assertThat(result.getSurname()).isEqualTo("Moore");
        assertThat(result.getTelephone()).isEqualTo("987-654-321");
    }

    @Test
    void updateUserInfoWithCorrectDtoAndInvalidUserIdThrowsNotFoundException() {

        Integer userId = 1;

        UserDto userDto = UserDto.builder()
                .login("PatMoore")
                .name("Patrick")
                .surname("Moore")
                .telephone("987-654-321")
                .build();

        when(repository.findById(userId)).thenThrow(new NotFoundException("User doesn't exist"));
        var msg = assertThrows(NotFoundException.class, () -> userService.updateUserInfo(userDto, userId));
        verify(repository, times(1)).findById(userId);

        assertThat(msg).isNotNull();
        assertThat(msg.getMessage()).isEqualTo("User doesn't exist");

        verify(repository, never()).save(any(User.class));
    }

    @Test
    void updateUserInfoWithNullDtoAndCorrectUserIdReturnNotUpdatedUser() {

        Integer userId = 1;

        User existedUser = User.builder()
                .surname("Moore1")
                .name("Patrick1")
                .telephone("123-456-789")
                .login("PatMoore1")
                .build();

        when(repository.findById(userId)).thenReturn(Optional.of(existedUser));
        when(userMapper.updateUserFromDto(null, Optional.of(existedUser).get())).thenReturn(existedUser);
        when(repository.save(existedUser)).thenReturn(existedUser);

        User result = userService.updateUserInfo(null, userId);

        verify(repository, times(1)).findById(userId);
        verify(userMapper, times(1)).updateUserFromDto(null, existedUser);
        verify(repository, times(1)).save(existedUser);

        assertThat(result).isNotNull();
        assertThat(result.getLogin()).isEqualTo("PatMoore1");
        assertThat(result.getName()).isEqualTo("Patrick1");
        assertThat(result.getSurname()).isEqualTo("Moore1");
        assertThat(result.getTelephone()).isEqualTo("123-456-789");

    }

    @Test
    void updateUserInfoWithCorrectDtoAndNullUserIdThrowsNotFoundException() {

        UserDto userDto = UserDto.builder()
                .login("PatMoore")
                .name("Patrick")
                .surname("Moore")
                .telephone("987-654-321")
                .build();

        var msg = assertThrows(NotFoundException.class, () -> userService.updateUserInfo(userDto, null));

        assertThat(msg).isNotNull();
        assertThat(msg.getMessage()).isEqualTo("Wrong userId");

    }

    @Test
    void updateUserInfoWithNullSomeDtoFieldsAndCorrectUserIdReturnUpdaterUser() {

        Integer userId = 1;

        UserDto userDto = UserDto.builder()
                .login("PatMoore")
                .name("Patrick")
                .surname(null)
                .telephone(null)
                .build();

        User existedUser = User.builder()
                .surname("Moore1")
                .name("Patrick1")
                .telephone("123-456-789")
                .login("PatMoore1")
                .build();

        User updatedUser = User.builder()
                .surname("Moore1")
                .name("Patrick")
                .telephone("123-456-789")
                .login("PatMoore")
                .build();

        when(repository.findById(userId)).thenReturn(Optional.of(existedUser));
        when(userMapper.updateUserFromDto(userDto, Optional.of(existedUser).get())).thenReturn(updatedUser);
        when(repository.save(updatedUser)).thenReturn(updatedUser);

        User result = userService.updateUserInfo(userDto, userId);

        verify(repository, times(1)).findById(userId);
        verify(userMapper, times(1)).updateUserFromDto(userDto, existedUser);
        verify(repository, times(1)).save(updatedUser);

        assertThat(result).isNotNull();
        assertThat(result.getLogin()).isEqualTo("PatMoore");
        assertThat(result.getName()).isEqualTo("Patrick");
        assertThat(result.getSurname()).isEqualTo("Moore1");
        assertThat(result.getTelephone()).isEqualTo("123-456-789");
    }

    @Test
    void changePasswordWithValidDtoAndValidUserId() {

        Integer userId = 1;

        ChangePasswordDto changePasswordDto = ChangePasswordDto.builder()
                .oldPassword("oldPassword")
                .newPassword("password1")
                .confirmPassword("password1")
                .build();

        User existedUser = User.builder()
                .password("encodedOldPassword")
                .build();

        when(repository.findById(userId)).thenReturn(Optional.of(existedUser));
        when(passwordEncoder.matches(changePasswordDto.getOldPassword(), existedUser.getPassword()))
                .thenReturn(true);
        when(passwordEncoder.matches(changePasswordDto.getNewPassword(), existedUser.getPassword()))
                .thenReturn(false);
        when(passwordEncoder.encode(changePasswordDto.getNewPassword())).thenReturn("encodedNewPassword");
        when(repository.save(existedUser)).thenReturn(existedUser);

        userService.changePassword(changePasswordDto, userId);

        verify(repository, times(1)).findById(userId);
        verify(passwordEncoder, times(1)).matches("oldPassword", "encodedOldPassword");
        verify(passwordEncoder, times(1)).matches(changePasswordDto.getNewPassword(), "encodedOldPassword");
        verify(passwordEncoder, times(1)).encode(changePasswordDto.getNewPassword());
        verify(repository, times(1)).save(existedUser);
        assertThat(existedUser.getPassword()).isEqualTo("encodedNewPassword");

    }

    @Test
    void changePasswordWithWrongUserIdThrowsNotFoundException() {

        ChangePasswordDto changePasswordDto = ChangePasswordDto.builder()
                .oldPassword("oldPassword")
                .newPassword("password1")
                .confirmPassword("password1")
                .build();

        Integer userId = 1;

        when(repository.findById(userId)).thenThrow(new NotFoundException(NO_USER_FOUND));

        var msg = assertThrows(NotFoundException.class, () -> userService.changePassword(changePasswordDto, userId));
        assertThat(msg).isNotNull();
        assertThat(msg.getMessage()).isEqualTo(NO_USER_FOUND);
        verify(repository, times(1)).findById(userId);
        verify(repository, never()).save(any(User.class));
    }

    @Test
    void changePasswordWithOldPasswordIncorrectThrowsInvalidCredentialsException() {

        Integer userId = 1;

        ChangePasswordDto changePasswordDto = ChangePasswordDto.builder()
                .oldPassword("wrongOldPassword")
                .newPassword("password1")
                .confirmPassword("password1")
                .build();


        User existedUser = User.builder()
                .password("encodedOldPassword")
                .build();

        when(repository.findById(userId)).thenReturn(Optional.of(existedUser));
        when(passwordEncoder.matches(changePasswordDto.getOldPassword(), existedUser.getPassword()))
                .thenReturn(false);

        var msg = assertThrows(InvalidCredentialsException.class, () -> userService.changePassword(changePasswordDto, userId));

        verify(repository, times(1)).findById(userId);
        verify(passwordEncoder, times(1)).matches(changePasswordDto.getOldPassword(), existedUser.getPassword());
        assertThat(msg).isNotNull();
        assertThat(msg.getMessage()).isEqualTo("Wrong password!");
        verify(repository, never()).save(any(User.class));

    }

    @Test
    void changePasswordWithTheSamePasswordAsOldOneThrowsInvalidCredentialsException() {

        Integer userId = 1;

        ChangePasswordDto changePasswordDto = ChangePasswordDto.builder()
                .oldPassword("encodedOldPassword")
                .newPassword("encodedOldPassword")
                .confirmPassword("encodedOldPassword")
                .build();


        User existedUser = User.builder()
                .password("encodedOldPassword")
                .build();

        when(repository.findById(userId)).thenReturn(Optional.of(existedUser));
        when(passwordEncoder.matches(changePasswordDto.getOldPassword(), existedUser.getPassword()))
                .thenReturn(true);
        when(passwordEncoder.matches(changePasswordDto.getNewPassword(), existedUser.getPassword()))
                .thenReturn(true);

        var msg = assertThrows(InvalidCredentialsException.class, () -> userService.changePassword(changePasswordDto, userId));

        verify(repository, times(1)).findById(userId);
        verify(passwordEncoder, times(2)).matches(changePasswordDto.getOldPassword(), existedUser.getPassword());
        verify(passwordEncoder, times(2)).matches(changePasswordDto.getNewPassword(), existedUser.getPassword());
        assertThat(msg).isNotNull();
        assertThat(msg.getMessage()).isEqualTo("New password and the old one are the same!");
        verify(repository, never()).save(any(User.class));

    }

    @Test
    void changePasswordWithNewPasswordDoNotMachConfirmationThrowsInvalidCredentialsException() {

        Integer userId = 1;

        ChangePasswordDto changePasswordDto = ChangePasswordDto.builder()
                .oldPassword("wrongOldPassword")
                .newPassword("password_")
                .confirmPassword("password1")
                .build();


        User existedUser = User.builder()
                .password("encodedOldPassword")
                .build();

        when(repository.findById(userId)).thenReturn(Optional.of(existedUser));
        when(passwordEncoder.matches(changePasswordDto.getOldPassword(), existedUser.getPassword()))
                .thenReturn(true);
        when(passwordEncoder.matches(changePasswordDto.getNewPassword(), existedUser.getPassword()))
                .thenReturn(false);

        var msg = assertThrows(InvalidCredentialsException.class, () -> userService.changePassword(changePasswordDto, userId));

        verify(repository, times(1)).findById(userId);
        verify(passwordEncoder, times(1)).matches(changePasswordDto.getOldPassword(), existedUser.getPassword());
        verify(passwordEncoder, times(1)).matches(changePasswordDto.getNewPassword(), existedUser.getPassword());
        assertThat(msg).isNotNull();
        assertThat(msg.getMessage()).isEqualTo("Passwords are not the same!");
        verify(repository, never()).save(any(User.class));
    }

    @Test
    void changePasswordWithCorrectDtoAndNullUserIdThrowsNotFoundException() {

        ChangePasswordDto changePasswordDto = ChangePasswordDto.builder()
                .oldPassword("wrongOldPassword")
                .newPassword("password_")
                .confirmPassword("password1")
                .build();

        var msg = assertThrows(NotFoundException.class, () -> userService.changePassword(changePasswordDto, null));

        assertThat(msg).isNotNull();
        assertThat(msg.getMessage()).isEqualTo(NO_USER_FOUND);

    }

    @Test
    void changeEmailWithCorrectDtoAndUserIdReturnNewJwtToken() {

        Integer userId = 1;

        ChangeEmailDto request = ChangeEmailDto.builder()
                .newEmail("John@mail.com")
                .confirmPassword("password")
                .build();

        User existedUser = User.builder()
                .id(userId)
                .login("PatMoore")
                .name("Patrick")
                .surname("Moore")
                .telephone("123-456-789")
                .role(Role.USER)
                .email("PatMoore@mail.com")
                .password("encodedPassword")
                .build();

        Map<String, Object> claims = Map.of(
                "id", existedUser.getId(),
                "login", existedUser.getLogin(),
                "name", existedUser.getName(),
                "surname", existedUser.getSurname(),
                "telephone", existedUser.getTelephone(),
                "role", existedUser.getRole()
        );

        when(repository.findById(userId)).thenReturn(Optional.of(existedUser));
        when(passwordEncoder.matches(request.getConfirmPassword(), existedUser.getPassword())).thenReturn(true);
        when(passwordEncoder.matches(request.getNewEmail(), existedUser.getEmail())).thenReturn(false);
        when(repository.save(existedUser)).thenReturn(existedUser);
        when(jwtService.generateToken(claims, existedUser)).thenReturn("jwtToken");

        AuthenticationResponseDto auth = userService.changeEmail(request, userId);

        verify(repository, times(1)).findById(userId);
        verify(passwordEncoder, times(1)).matches(request.getConfirmPassword(), existedUser.getPassword());
        verify(passwordEncoder, times(1)).matches(request.getNewEmail(), "PatMoore@mail.com");
        verify(repository, times(1)).save(existedUser);
        verify(jwtService, times(1)).generateToken(claims, existedUser);

        assertThat(auth).isNotNull();
        assertThat(auth.getToken()).isEqualTo("jwtToken");
        assertThat(existedUser.getEmail()).isEqualTo(request.getNewEmail());
    }

    @Test
    void changeEmailWithIncorrectUserIdThrowsNotFoundException() {

        ChangeEmailDto request = ChangeEmailDto.builder()
                .newEmail("John@mail.com")
                .confirmPassword("password")
                .build();

        Integer userId = 1;

        when(repository.findById(1)).thenThrow(new NotFoundException(NO_USER_FOUND));

        var msg = assertThrows(NotFoundException.class, () -> userService.changeEmail(request, userId));

        verify(repository, times(1)).findById(userId);

        assertThat(msg).isNotNull();
        assertThat(msg.getMessage()).isEqualTo(NO_USER_FOUND);

        verify(repository, never()).save(any(User.class));
    }

    @Test
    void changeEmailWithNewEmailTheSameAsOldOneThrowsInvalidCredentialsException() {

        ChangeEmailDto request = ChangeEmailDto.builder()
                .newEmail("PatMoore@mail.com")
                .confirmPassword("password")
                .build();

        Integer userId = 1;
        User existedUser = User.builder()
                .email("PatMoore@mail.com")
                .password("encodedPassword")
                .build();

        when(repository.findById(userId)).thenReturn(Optional.of(existedUser));
        when(passwordEncoder.matches(request.getConfirmPassword(), existedUser.getPassword())).thenReturn(true);
        when(passwordEncoder.matches(request.getNewEmail(), existedUser.getEmail())).thenReturn(true);

        var msg = assertThrows(InvalidCredentialsException.class, () -> userService.changeEmail(request, userId));

        verify(repository, times(1)).findById(userId);
        verify(passwordEncoder, times(1)).matches(request.getConfirmPassword(), existedUser.getPassword());
        verify(passwordEncoder, times(1)).matches(request.getNewEmail(), existedUser.getEmail());

        assertThat(msg).isNotNull();
        assertThat(msg.getMessage()).isEqualTo("New email and the old one are the same!");

        verify(repository, never()).save(any(User.class));

    }

    @Test
    void changeEmailWithIncorrectConfirmationThrowsInvalidCredentialsException() {

        ChangeEmailDto request = ChangeEmailDto.builder()
                .newEmail("John@mail.com")
                .confirmPassword("password")
                .build();

        Integer userId = 1;
        User existedUser = User.builder()
                .email("PatMoore@mail.com")
                .password("encodedPassword")
                .build();

        when(repository.findById(userId)).thenReturn(Optional.of(existedUser));
        when(passwordEncoder.matches(request.getConfirmPassword(), existedUser.getPassword())).thenReturn(false);

        var msg = assertThrows(InvalidCredentialsException.class, () -> userService.changeEmail(request, userId));

        verify(repository, times(1)).findById(userId);
        verify(passwordEncoder, times(1)).matches(request.getConfirmPassword(), existedUser.getPassword());

        assertThat(msg).isNotNull();
        assertThat(msg.getMessage()).isEqualTo("Wrong password!");

        verify(repository, never()).save(any(User.class));

    }

    @Test
    void changeEmailWithCorrectDtoAndNullUserIdThrowsNotFoundException() {

        ChangeEmailDto request = ChangeEmailDto.builder()
                .newEmail("John@mail.com")
                .confirmPassword("password")
                .build();

        var msg = assertThrows(NotFoundException.class, () -> userService.changeEmail(request, null));

        assertThat(msg).isNotNull();
        assertThat(msg.getMessage()).isEqualTo(NO_USER_FOUND);

    }

    @Test
    void saveUserWithCorrectUserReturnsSavedUser() {

        User myUser = User.builder()
                .id(1)
                .login("PatMoore")
                .email("PatMoore@mail.com")
                .name("Patrick")
                .surname("Moore")
                .password("password")
                .role(Role.USER)
                .telephone("123-456-789")
                .build();

        when(repository.save(myUser)).thenReturn(myUser);

        userService.saveUser(myUser);

        verify(repository, times(1)).save(myUser);
    }

    @Test
    void saveUserWithMissingFieldsReturnsSavedUser() {
        User validUser = User.builder()
                .name("John")
                .email("PatMoore@mail.com")
                .password("password")
                .id(1)
                .role(Role.USER)
                .login("PatMoore")
                .build();

        when(repository.save(validUser)).thenReturn(validUser);

        userService.saveUser(validUser);

        verify(repository, times(1)).save(any(User.class));
        assertThat(validUser.getName()).isEqualTo("John");
    }

    @Test
    void getAllReturnAllUsers() {

        User user1 = User.builder()
                .id(1)
                .login("JohnDoe")
                .name("John")
                .surname("Doe")
                .telephone("987-654-321")
                .role(Role.USER)
                .email("JohnDoe@mail.com")
                .password("encodedPassword1")
                .build();

        User user2 = User.builder()
                .id(2)
                .login("PatMoore")
                .name("Patrick")
                .surname("Moore")
                .telephone("123-456-789")
                .role(Role.USER)
                .email("PatMoore@mail.com")
                .password("encodedPassword2")
                .build();


        List<User> userList = List.of(user1, user2);

        when(repository.findAll()).thenReturn(userList);

        List<User> users = userService.getAll();

        verify(repository, times(1)).findAll();
        assertThat(users).isNotEmpty();
        assertThat(users).hasSize(2);
        assertThat(users).containsExactlyInAnyOrder(user1, user2);

    }

}