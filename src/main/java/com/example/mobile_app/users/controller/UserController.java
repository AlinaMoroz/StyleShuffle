package com.example.mobile_app.users.controller;

import com.example.mobile_app.users.dto.NewUserRequestDto;
import com.example.mobile_app.users.dto.NewUserResponseDto;
import com.example.mobile_app.users.dto.UserReadDto;
import com.example.mobile_app.users.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping({"/id"})
    public NewUserResponseDto findById(@PathVariable Long id){
        log.info("Attempting to get user by ID: {}", id);
        return userService.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


    @DeleteMapping({"/id"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        log.info("Attempting to delete user by ID: {}", id);

        if(!userService.deleteById(id)){
            log.warn("User with ID: {} not found", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping({"/email"})
    public UserReadDto findByEmail(@PathVariable String email){
        log.info("Attempting to find user by email: {}",email);

        return userService.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewUserResponseDto create(@Valid @RequestBody NewUserRequestDto user){
        log.info("Attempting to create user with email: {}", user.getEmail());

        return userService.create(user);
    }

}
