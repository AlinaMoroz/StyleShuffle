package com.example.mobile_app.users.service;


import com.example.mobile_app.exception.EntityCreationException;
import com.example.mobile_app.users.dto.NewUserRequestDto;
import com.example.mobile_app.users.dto.NewUserResponseDto;
import com.example.mobile_app.users.dto.UserReadDto;
import com.example.mobile_app.users.dto.UserUpdateDto;
import com.example.mobile_app.users.mapper.UserMapper;
import com.example.mobile_app.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Finds a user by their ID and maps them to a NewUserResponseDto
     *
     * @param id The ID of the user to find
     * @return An Optional with the NewUserResponseDto if the user is present; otherwise, empty.
     */
    public Optional<NewUserResponseDto> findById(Long id) {
        var newUserResponseDto = userRepository.findById(id)
                .map(userMapper::toNewUserResponseDto);

        newUserResponseDto.ifPresentOrElse(
                user -> log.info("User found: {}", user),
                () -> log.warn("User with id {} not found", id)
        );
        return newUserResponseDto;
    }

    /**
     * Deletes a user by their ID
     *
     * @param id The ID of the user delete
     * @return {@code true} if successful, otherwise {@code false}
     */
    @Transactional
    public boolean deleteById(Long id) {
        return userRepository.findById(id)
                .map(entity -> {
                    userRepository.delete(entity);
                    log.info("User with ID {} is deleted", id);
                    return true;
                }).orElseGet(() -> {
                    log.warn("User with ID {} was not found and cannot be deleted.", id);
                    return false;
                });
    }

    /**
     * Finds a user by their email address and maps the result to a UserReadDto
     *
     * @param email The email address of the user to find.
     * @return Optional of UserReadDto
     */

    public Optional<UserReadDto> findByEmail(String email) {

        var user = userRepository.findByEmail(email);
        var userReadDto = user.map(userMapper::toUserReadDto);

        if (user.isPresent()) {
            log.info("User with email {} found and mapped to UserReadDto.", email);
        } else {
            log.warn("No user found with email: {}", email);
        }
        return userReadDto;
    }


    /**
     * Creates a new user in the system by mapping the provided request DTO to a User entity,
     * saving the user in the database, and then mapping the saved entity to a response DTO.
     *
     * @param userDto userDto The DTO containing the user's registration details
     * @return NewUserResponseDto if user is saved correctly.
     * @throws EntityCreationException if an unexpected error occurs during the mapping or saving process.
     */
    @Transactional
    public NewUserResponseDto create(NewUserRequestDto userDto) {
        return Optional.of(userDto)
                .map(userMapper::toUser)
                .map(user -> {
                    log.info("Mapping NewUserRequestDto to User entity: {}", user);
                    return userRepository.save(user);
                })
                .map(savedUser -> {
                    log.info("User with ID {} successfully saved.", savedUser.getId());
                    return userMapper.toNewUserResponseDto(savedUser);
                })
                .orElseThrow(() -> {
                    log.error("Unexpected error during user creation for");
                    return new EntityCreationException("Failed to create user");
                });
    }


    /**
     * Updates an existing user by their ID using the provided UserUpdateDto.
     * <p>
     * This method finds the user by ID, updates the user's fields with the values
     * from the UserUpdateDto, saves the updated entity to the database, and then
     * returns the updated user's data as a UserReadDto.
     *
     * @param id        The ID of the user to update.
     * @param updateDto The DTO containing the updated user details
     * @return Optional of UserReadDto containing the updated user information.
     */
    @Transactional
    public Optional<UserReadDto> update(Long id, UserUpdateDto updateDto) {
        return userRepository.findById(id).map(
                entity -> {
                    log.info("User with ID {} found. Updating user with data: {}", id, updateDto);
                    userMapper.updateUser(updateDto, entity);
                    log.info("Mapped UserUpdateDto to User entity: {}", entity);

                    var entity1 = userRepository.save(entity);
                    userRepository.flush();
                    log.info("User with ID {} successfully updated and saved.", entity1.getId());

                    return userMapper.toUserReadDto(entity1);
                }
        );
    }

    /**
     * Retrieves a paginated list of users and maps them to UserReadDto objects.
     *
     * @param pageable The pagination and sorting information.
     * @return A paginated list of UserReadDto objects.
     */
    public Page<UserReadDto> findAll(Pageable pageable) {
        log.info("Fetching all users with pagination: page number = {}, page size = {}", pageable.getPageNumber(), pageable.getPageSize());
        var readDtos = userRepository.findAll(pageable)
                .map(userMapper::toUserReadDto);

        log.info("Fetched {} users on page number {}", readDtos.getContent().size(), pageable.getPageNumber());

        return readDtos;
    }
}
