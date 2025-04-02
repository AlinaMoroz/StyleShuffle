package com.example.mobile_app.integration.service;

import com.example.mobile_app.integration.IntegrationTestBase;
import com.example.mobile_app.users.dto.NewUserRequestDto;
import com.example.mobile_app.users.dto.UserUpdateDto;
import com.example.mobile_app.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
public class UserServiceIT extends IntegrationTestBase {

    private final UserService userService;

    private static final Long USER_ID = 1L;
    private static final String EMAIL = "ivan@example.com";

    @Test
    void findById() {
        var actualResult = userService.findById(USER_ID);

        assertTrue(actualResult.isPresent());
        actualResult.ifPresent(user -> {
            assertEquals(USER_ID, user.id());
            assertEquals(EMAIL, user.email());
        });
    }

    @Test
    void findByEmail() {
        var actualResult = userService.findByEmail(EMAIL);

        assertTrue(actualResult.isPresent());
        actualResult.ifPresent(user -> {
            assertEquals(USER_ID, user.id());
            assertEquals(EMAIL, user.email());
        });
    }

    @Test
    void create() {
        final String EMAIL = "test@example.com";
        final String PASSWORD = "password";
        final String USERNAME = "test";

        var userCreateDto = new NewUserRequestDto(
                EMAIL,
                PASSWORD,
                USERNAME);

        var actualResult = userService.create(userCreateDto);

        assertEquals(EMAIL, actualResult.email());
        assertEquals(USERNAME, actualResult.username());
    }

    @Test
    void update() {
        final String NAME = "Ivan Ivanov";
        final String AVATAR = "avatar";
        final String SIZE = "M";
        final String SURNAME = "Ivanov";
        final String USERNAME = "ivan";

        var userUpdateDto = new UserUpdateDto(
                NAME,
                AVATAR,
                SIZE,
                SURNAME,
                USERNAME
        );

        var actualResult = userService.update(USER_ID, userUpdateDto);


        actualResult.ifPresent(user -> {
            assertEquals(USER_ID, user.id());
            assertEquals(NAME, user.name());
            assertEquals(EMAIL, user.email());
            assertEquals(AVATAR, user.avatar());
            assertEquals(SIZE, user.size());
            assertEquals(SURNAME, user.surname());
            assertEquals(USERNAME, user.username());
        });

    }

    @Test
    void delete() {
        assertTrue(userService.deleteById(USER_ID));
        assertFalse(userService.findById(USER_ID).isPresent());
    }

    @Test
    void findAll() {
        Pageable pageable = PageRequest.of(0, 3);
        var actualResult = userService.findAll(pageable);

        assertTrue(actualResult.hasContent());
        assertEquals(3, actualResult.getContent().size(), "The list should have size 3");
    }
}
