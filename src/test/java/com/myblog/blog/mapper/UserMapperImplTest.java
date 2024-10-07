package com.myblog.blog.mapper;

import com.myblog.blog.dto.UserDto;
import com.myblog.blog.model.Role;
import com.myblog.blog.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperImplTest {

    private UserMapperImpl userMapper;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapperImpl();
    }

    @Test
    public void itShouldUpdateUserFromUserDto() {
        UserDto userDto = new UserDto(
                "PatMoore",
                "Patrick",
                "Moore",
                "123-456-789"
        );

        User user = User.builder()
                .id(1)
                .login("PatMoore1")
                .password("password")
                .email("patrick.moore@mail.com")
                .name("Patrick1")
                .surname("Moore1")
                .role(Role.USER)
                .telephone("987-654-321")
                .build();

        userMapper.updateUserFromDto(userDto, user);

        assertEquals(userDto.getLogin(), user.getLogin());
        assertEquals(userDto.getName(), user.getName());
        assertEquals(userDto.getSurname(), user.getSurname());
        assertEquals(userDto.getTelephone(), user.getTelephone());

    }

    @Test
    public void itShouldNotUpdateWhenDtoIsNull() {
        UserDto userDto = null;

        User user = User.builder()
                .id(1)
                .login("PatMoore1")
                .password("password")
                .email("patrick.moore@mail.com")
                .name("Patrick1")
                .surname("Moore1")
                .role(Role.USER)
                .telephone("987-654-321")
                .build();

        userMapper.updateUserFromDto(userDto, user);

        assertEquals("PatMoore1", user.getLogin());
        assertEquals("Patrick1", user.getName());
        assertEquals("Moore1", user.getSurname());
        assertEquals("987-654-321", user.getTelephone());


    }

    @Test
    public void itShouldPartialUpdateWhenFieldIsNull1() {
        UserDto userDto = UserDto.builder()
                .name("Patrick")
                .surname("Moore")
                .build();

        User user = User.builder()
                .id(1)
                .login("PatMoore1")
                .password("password")
                .email("patrick.moore@mail.com")
                .name("Patrick1")
                .surname("Moore1")
                .role(Role.USER)
                .telephone("987-654-321")
                .build();

        userMapper.updateUserFromDto(userDto, user);

        assertEquals(userDto.getName(), user.getName());
        assertEquals(userDto.getSurname(), user.getSurname());
    }

    @Test
    public void itShouldPartialUpdateWhenFieldIsNull2() {
        UserDto userDto = UserDto.builder()
                .login("PatMoore")
                .telephone("123-456-789")
                .build();

        User user = User.builder()
                .id(1)
                .login("PatMoore1")
                .password("password")
                .email("patrick.moore@mail.com")
                .name("Patrick1")
                .surname("Moore1")
                .role(Role.USER)
                .telephone("987-654-321")
                .build();

        userMapper.updateUserFromDto(userDto, user);

        assertEquals(userDto.getLogin(), user.getLogin());
        assertEquals(userDto.getTelephone(), user.getTelephone());
    }

    @Test
    public void itShouldNotUpdateWhenFieldsAreNull() {
        UserDto userDto = UserDto.builder()
                .login(null)
                .name(null)
                .surname(null)
                .telephone(null)
                .build();

        User user = User.builder()
                .id(1)
                .login("PatMoore1")
                .password("password")
                .email("patrick.moore@mail.com")
                .name("Patrick1")
                .surname("Moore1")
                .role(Role.USER)
                .telephone("987-654-321")
                .build();

        userMapper.updateUserFromDto(userDto, user);

        assertEquals("PatMoore1", user.getLogin());
        assertEquals("Patrick1", user.getName());
        assertEquals("Moore1", user.getSurname());
        assertEquals("987-654-321", user.getTelephone());
    }
}