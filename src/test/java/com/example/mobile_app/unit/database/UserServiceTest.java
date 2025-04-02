package com.example.mobile_app.unit.database;

import com.example.mobile_app.exception.EntityCreationException;

import com.example.mobile_app.users.dto.NewUserRequestDto;
import com.example.mobile_app.users.dto.NewUserResponseDto;
import com.example.mobile_app.users.dto.UserReadDto;
import com.example.mobile_app.users.dto.UserUpdateDto;
import com.example.mobile_app.users.mapper.UserMapper;
import com.example.mobile_app.users.modal.User;
import com.example.mobile_app.users.repository.UserRepository;
import com.example.mobile_app.users.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserService userService;


    private static final Long USER_ID = 1L;
    private static final String USER_NAME = "ivan_ivanov";
    private static final String EMAIL = "ivan@example.com";
    private static final String NAME = "Иван";
    private static final String SURNAME = "Иванов";
    private static final String AVATAR = "photo1.png";
    private static final String PASSWORD = "1234";
    private User user;
    private User updatedUser;
    private NewUserResponseDto responseDto;
    private NewUserRequestDto requestDto;
    private UserUpdateDto updateDto;
    private UserReadDto readDto;
    private UserReadDto expectedReadDto;

    @BeforeEach
    void setUp() {

        user = new User();
        user.setId(USER_ID);
        user.setName(USER_NAME);

        responseDto = new NewUserResponseDto(USER_ID, USER_NAME, EMAIL);
        requestDto = new NewUserRequestDto(EMAIL, PASSWORD, USER_NAME);
        updateDto = new UserUpdateDto(NAME, AVATAR, "38", SURNAME, USER_NAME);

        readDto = new UserReadDto(USER_ID, NAME, EMAIL, AVATAR, "38", SURNAME, USER_NAME);

        User user = new User();
        user.setId(USER_ID);
        user.setName("Old Name");

        UserUpdateDto updateDto = new UserUpdateDto(
                "New Name",
                "New Avatar",
                "L",
                "NewSurname",
                "NewUsername"
        );


        updatedUser = new User();
        updatedUser.setId(USER_ID);
        updatedUser.setName(updateDto.getName());
        updatedUser.setAvatar(updateDto.getAvatar());
        updatedUser.setSize(updateDto.getSize());
        updatedUser.setSurname(updateDto.getSurname());
        updatedUser.setUsername(updateDto.getUsername());

        expectedReadDto = new UserReadDto(
                USER_ID,
                updateDto.getName(),
                "existingEmail@example.com",
                updateDto.getAvatar(),
                updateDto.getSize(),
                updateDto.getSurname(),
                updateDto.getUsername()
        );


    }

    @Nested
    @Tag("findById")
    class FindByIdTest {
        @Test
        void UserFound() {

            when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
            when(userMapper.toNewUserResponseDto(user)).thenReturn(responseDto);

            Optional<NewUserResponseDto> result = userService.findById(USER_ID);

            assertTrue(result.isPresent(), "User should be find");
            assertEquals(USER_ID, result.get().id(), "User ID should match");
            assertEquals(USER_NAME, result.get().username(), "User name should match");
            assertEquals(EMAIL, result.get().email(), "Email should match");
            verify(userRepository, times(1)).findById(USER_ID);
            verify(userMapper, times(1)).toNewUserResponseDto(user);
        }

        @Test
        void UserNotFound() {
            when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());

            Optional<NewUserResponseDto> result = userService.findById(USER_ID);

            assertTrue(result.isEmpty(), "The user should not be found");
            verify(userRepository, times(1)).findById(USER_ID);
        }

        @Test
        void NullId() {
            Long userId = null;

            Optional<NewUserResponseDto> result = userService.findById(userId);

            assertTrue(result.isEmpty(), "The result should be empty if the identifier is zero");
        }
    }


    @Nested
    @Tag("deleteById")
    class DeleteTest {
        @Test
        void userFoundAndDeleted() {
            when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));

            boolean result = userService.deleteById(USER_ID);

            assertTrue(result, "The user should be deleted");
            verify(userRepository, times(1)).findById(USER_ID);
            verify(userRepository, times(1)).delete(user);
        }

        @Test
        void userNotFound() {
            when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());

            boolean result = userService.deleteById(USER_ID);

            assertFalse(result, "The user should not be deleted");
            verify(userRepository, times(1)).findById(USER_ID);
            verify(userRepository, never()).delete(any());
        }

        @Test
        void nullId() {
            Long userId = null;

            boolean result = userService.deleteById(userId);

            assertFalse(result, "The result should be false if the ID is null");
            verify(userRepository, times(1)).findById(any());
            verify(userRepository, never()).delete(any());
        }

    }


    @Nested
    @Tag("findByEmail")
    class FindByEmailTest {
        @Test
        void UserFound() {
            when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.of(user));
            when(userMapper.toUserReadDto(user)).thenReturn(readDto);

            var result = userService.findByEmail(EMAIL);

            assertTrue(result.isPresent(), "User should be found");
            assertEquals(EMAIL, result.get().email(), "The user's email address should match");
            assertEquals(USER_NAME, result.get().username(), "Username should match");
        }


        @Test
        void UserNotFount() {
            when(userService.findByEmail(EMAIL)).thenReturn(Optional.empty());

            var result = userService.findByEmail(EMAIL);

            assertFalse(result.isPresent(), "User should not be found");
            verify(userRepository, times(1)).findByEmail(EMAIL);
        }

        @Test
        void NullEmail() {
            String email = null;

            var result = userService.findByEmail(email);

            assertTrue(result.isEmpty(), "Result should be empty when email is null");
        }

    }


    @Nested
    @Tag("create")
    class CreatTest {
        @Test
        void success() {
            when(userMapper.toUser(requestDto)).thenReturn(user);
            when(userRepository.save(user)).thenReturn(user);
            when(userMapper.toNewUserResponseDto(user)).thenReturn(responseDto);

            var result = userService.create(requestDto);

            assertNotNull(result, "Result should not be null");
            assertEquals(result.id(), USER_ID, "The user's ID should be match");
            assertEquals(result.username(), USER_NAME, "The user's username should be match");
            assertEquals(result.email(), EMAIL, "The user's email should be match");
        }

        @Test
        void saveError() {
            when(userMapper.toUser(requestDto)).thenReturn(user);
            when(userRepository.save(user)).thenThrow(new EntityCreationException("Failed to create user"));

            assertThrows(EntityCreationException.class, () -> userService.create(requestDto),
                    "Expected EntityCreationException to be thrown when saving the user");

            verify(userMapper, times(1)).toUser(requestDto);
            verify(userRepository, times(1)).save(user);
        }
    }

    @Nested
    @Tag("update")
    class UpdateTest {

        @Test
        void success() {
            when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
            doAnswer(invocation -> {
                if (updateDto.getName() != null) user.setName(updateDto.getName());
                if (updateDto.getAvatar() != null) user.setAvatar(updateDto.getAvatar());
                if (updateDto.getSize() != null) user.setSize(updateDto.getSize());
                if (updateDto.getUsername() != null) user.setUsername(updateDto.getUsername());
                if (updateDto.getSurname() != null) user.setSurname(updateDto.getSurname());
                return null;
            }).when(userMapper).updateUser(eq(updateDto), eq(user));

            when(userRepository.save(user)).thenReturn(updatedUser);
            when(userMapper.toUserReadDto(updatedUser)).thenReturn(expectedReadDto);

            Optional<UserReadDto> resultOpt = userService.update(USER_ID, updateDto);


            assertTrue(resultOpt.isPresent(), "Result should be present");
            UserReadDto result = resultOpt.get();
            assertEquals(expectedReadDto, result, "Updated user DTO should match the expected DTO");
            verify(userRepository, times(1)).findById(USER_ID);
            verify(userMapper, times(1)).updateUser(updateDto, user);
            verify(userRepository, times(1)).save(user);
            verify(userRepository, times(1)).flush();
            verify(userMapper, times(1)).toUserReadDto(updatedUser);
        }

        @Test
        void userNotFound() {
            when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());

            Optional<UserReadDto> resultOpt = userService.update(USER_ID, updateDto);

            assertFalse(resultOpt.isPresent(), "Result should be empty when user is not found");
            verify(userRepository, times(1)).findById(USER_ID);
            verify(userRepository, never()).save(any());
            verify(userMapper, never()).updateUser(any(), any());
        }
    }
}