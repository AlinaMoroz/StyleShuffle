package com.example.mobile_app.clothes.service;

import com.example.mobile_app.clothes.Type;
import com.example.mobile_app.clothes.dto.ClothCreateDto;
import com.example.mobile_app.clothes.dto.ClothReadDto;
import com.example.mobile_app.clothes.mapper.ClothMapper;
import com.example.mobile_app.clothes.repository.ClothRepository;
import com.example.mobile_app.looks_clothes.LookClothRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ClothService {


    private final ClothRepository clothRepository;
    private final ClothMapper clothMapper;
    private final LookClothRepository lookClothRepository;

    /**
     * Finds a cloth by their ID and maps them to a ClothReadDto
     * @param id The ID of the cloth to find
     * @return An Optional with the ClothReadDto if the Cloth present; otherwise, empty
     */
    public Optional<ClothReadDto> findById(Long id) {
        var maybeClothReadDTO = clothRepository.findById(id)
                .map(clothMapper::toClothReadDto);

        maybeClothReadDTO.ifPresentOrElse(
                user -> log.info("User found: {}",user),
                () -> log.warn("User with id {} not found", id)
        );

        return maybeClothReadDTO;
    }


    /**
     * Finds all clothes associated with a specific set by its ID.
     *
     * @param setId The ID of the set for which clothes are to be retrieved.
     * @return List of ClothReadDto representing all clothes in the set.
     */
    public List<ClothReadDto> findAllClothBySetId(Long setId) {

        var clothReadDtos = lookClothRepository.findAllClothByLookId(setId)
                .stream()
                .map(clothMapper::toClothReadDto)
                .toList();

        if(clothReadDtos.size() == 0){
            log.info("Set with id {} does have any clothes");
        }else {
            log.info("Set with id {} contains {} clothes.", setId, clothReadDtos.size());
        }
        return clothReadDtos;
    }


    /**
     * Creates a new Cloth entity from the given DTO, saves it to the repository, and returns the mapped ClothReadDto.
     *
     * @param createDto The DTO containing details for the new Cloth entity.
     * @return ClothReadDto representing the saved Cloth entity.
     * @throws RuntimeException if the creation process fails.
     */
    @Transactional
    public ClothReadDto create(ClothCreateDto createDto) {
        return Optional.of(createDto)
                .map(clothMapper::toCloth)
                .map(cloth -> {
                    log.info("Mapped Cloth entity: {}", cloth);
                    return clothRepository.save(cloth);
                })
                .map(savedCloth -> {
                    log.info("Saved Cloth entity: {}", savedCloth);
                    return clothMapper.toClothReadDto(savedCloth);
                })
                .orElseThrow(() -> {
                    log.error("Cloth creation failed for DTO: {}", createDto);
                    return new RuntimeException("Failed to create Cloth");
                });

    }

    /**
     * Finds all clothes for a specific user by their ID and type
     * @param userId The ID of the user
     * @param type A type of the cloth to filter
     * @return List of ClothReadDto representing the clothes for the specified user and type.
     */
    public List<ClothReadDto> findAllByUserIdAndType(Long userId, Type type) {
        var clothReadDtos = clothRepository.findAllByUserIdAndType(userId, type)
                .stream().map(clothMapper::toClothReadDto).toList();

        if(clothReadDtos.size() == 0){
            log.info("No clothes found for user with ID {} and type {}", userId, type);
        }else {
            log.info("Found {} clothes for user with ID {} and type {}", clothReadDtos.size(), userId, type);
        }

        return clothReadDtos;

    }

    /**
     * Deletes a cloth by ID
     * @param id The ID of the cloth delete
     * @return {@code true} if successful, otherwise {@code false}
     */
    @Transactional
    public boolean deleteById(Long id) {
        return clothRepository.findById(id)
                .map(entity -> {
                    clothRepository.delete(entity);
                    clothRepository.flush();

                    log.info("Cloth with id {} was deleted", id);
                    return true;
                })
                .orElseGet(() ->{
                    log.warn("Cloth with id {} not found and cannot be deleted");
                    return false;
                });

    }
}
