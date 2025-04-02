package com.example.mobile_app.users.controller;

import com.example.mobile_app.exception.EntityNotFoundException;
import com.example.mobile_app.users.dto.NewUserRequestDto;
import com.example.mobile_app.users.dto.NewUserResponseDto;
import com.example.mobile_app.users.dto.UserReadDto;
import com.example.mobile_app.users.dto.UserUpdateDto;
import com.example.mobile_app.users.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @Operation(summary = "Get user by ID", description = "Get user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping({"/{id}"})
    public NewUserResponseDto findById(@PathVariable Long id) {
        log.info("Attempting to get user by ID: {}", id);
        return userService.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Find user by email",
            description = "Find user by email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping({"/email/{email}"})
    public UserReadDto findByEmail(@PathVariable String email) {
        log.info("Attempting to find user by email: {}", email);

        return userService.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: {}" + email));
    }

    @Operation(summary = "Delete user by ID", description = "Delete user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        log.info("Attempting to delete user by ID: {}", id);

        if (!userService.deleteById(id)) {
            log.warn("User with ID: {} not found", id);
            throw new EntityNotFoundException("User with ID: " + id + " not found");
        }
    }

    @Operation(summary = "Create new user", description = "Create new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewUserResponseDto create(@Valid @RequestBody NewUserRequestDto user) {
        log.info("Attempting to create user with email: {}", user.getEmail());

        return userService.create(user);
    }

    @Operation(summary = "Update user by ID", description = "Update user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping({"/{id}"})
    public UserReadDto update(@PathVariable Long id, @Valid @RequestBody UserUpdateDto updateDto) {
        log.info("Attempting to update user with ID: {} and update data: {}", id, updateDto);
        return userService.update(id, updateDto)
                .orElseThrow(() -> new EntityNotFoundException("User with ID: " + id + " not found"));
    }

    @Operation(summary = "Find all users", description = "Find all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users found")
    })
    @GetMapping
    public Page<UserReadDto> findAll(Pageable pageable) {
        log.info("Attempting to find all users with pageable: {}", pageable);
        return userService.findAll(pageable);
    }
}
