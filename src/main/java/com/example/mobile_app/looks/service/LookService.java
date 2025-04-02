package com.example.mobile_app.looks.service;

import com.example.mobile_app.clothes.model.Cloth;
import com.example.mobile_app.clothes.repository.ClothRepository;
import com.example.mobile_app.exception.EntityCreationException;
import com.example.mobile_app.exception.EntityNotFoundException;
import com.example.mobile_app.looks.LookRepository;
import com.example.mobile_app.looks.dto.LookCreateDto;
import com.example.mobile_app.looks.dto.LookReadDto;
import com.example.mobile_app.looks.dto.LookUpdateDto;
import com.example.mobile_app.looks.mapper.LookMapper;
import com.example.mobile_app.looks.model.Look;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class LookService {


    private final LookRepository lookRepository;
    private final LookMapper lookMapper;
    private final ClothRepository clothRepository;

    public Optional<LookReadDto> findById(Long id) {
        Optional<LookReadDto> result = lookRepository.findById(id)
                .map(lookMapper::toLookReadDto);
        if (result.isPresent()) {
            log.info("Look with ID {} found", id);
        } else {
            log.warn("Look with ID {} not found", id);
        }
        return result;
    }

    public Page<LookReadDto> findAllByUserId(Long userId, Pageable pageable) {
        Page<LookReadDto> result = lookRepository.findAllByUserId(userId, pageable)
                .map(lookMapper::toLookReadDto);
        log.info("Found {} Looks for user ID {}", result.getTotalElements(), userId);
        return result;
    }

    @Transactional
    public boolean deleteById(Long id) {
        return lookRepository.findById(id)
                .map(entity -> {
                    lookRepository.delete(entity);
                    lookRepository.flush();
                    log.info("Look with ID {} deleted successfully", id);
                    return true;
                }).orElseGet(() -> {
                    log.warn("Look with ID {} not found for deletion", id);
                    return false;
                });
    }

    @Transactional
    public Optional<LookReadDto> update(Long id, LookUpdateDto lookUpdateDto) {
        return lookRepository.findById(id)
                .map(entity -> {
                    lookMapper.updateLook(lookUpdateDto, entity);
                    lookRepository.save(entity);
                    log.info("Look with ID {} updated successfully", id);
                    return lookMapper.toLookReadDto(entity);
                });
    }

    @Transactional
    public LookReadDto create(LookCreateDto lookCreateDto) {
        return Optional.ofNullable(lookCreateDto)
                .map(dto -> {
                    Look look = lookMapper.toLook(dto);
                    Set<Cloth> clothSet = getCloths(dto);
                    addClothSetToLook(look, clothSet);
                    var savedLook = lookRepository.save(look);
                    log.info("Look created successfully with ID: {}", savedLook.getId());
                    return savedLook;
                })
                .map(lookMapper::toLookReadDto)
                .orElseThrow(() -> {
                    log.error("Creation failed: LookCreateDto is null or invalid");
                    return new EntityCreationException("Creation failed: LookCreateDto is null or invalid");
                });
    }

    private void addClothSetToLook(Look look, Set<Cloth> clothSet) {
        log.info("Adding {} cloth(s) to Look with ID: {}", clothSet.size(), look.getId());
        clothSet.forEach(cloth -> log.debug("Adding cloth with ID: {}", cloth.getId()));
        clothSet.forEach(look::addCloth);
    }

    private Set<Cloth> getCloths(LookCreateDto dto) {
        log.info("Retrieving cloths for LookCreateDto with cloth IDs: {}", dto.getClothIds());
        return dto.getClothIds().stream()
                .map(clothId -> clothRepository.findById(clothId)
                        .orElseThrow(() -> {
                            log.error("Cloth not found with ID: {}", clothId);
                            return new EntityCreationException("Cloth not found with id: " + clothId);
                        }))
                .collect(Collectors.toSet());
    }

    @Transactional
    public void addClothToLook(Long lookId, Long clothId) {
        validateLookAndClothIds(lookId, clothId);

        var look = lookRepository.findById(lookId)
                .orElseThrow(() -> {
                    log.error("Look not found with ID: {}", lookId);
                    return new EntityNotFoundException("Look not found with id: " + lookId);
                });
        var cloth = clothRepository.findById(clothId)
                .orElseThrow(() -> {
                    log.error("Cloth not found with ID: {}", clothId);
                    return new EntityNotFoundException("Cloth not found with id: " + clothId);
                });

        look.addCloth(cloth);
        lookRepository.save(look);
        log.info("Cloth with ID: {} successfully added to Look with ID: {}", clothId, lookId);
    }

    @Transactional
    public void removeClothFromLook(Long lookId, Long clothId) {
        validateLookAndClothIds(lookId, clothId);

        var look = lookRepository.findById(lookId)
                .orElseThrow(() -> {
                    log.error("Look not found with ID: {}", lookId);
                    return new EntityNotFoundException("Look not found with id: " + lookId);
                });
        clothRepository.findById(clothId)
                .ifPresentOrElse(
                        cloth -> {
                            look.removeCloth(cloth);
                            lookRepository.save(look);
                            log.info("Cloth with ID: {} removed from Look with ID: {}", clothId, lookId);
                        },
                        () -> {
                            log.error("Cloth not found with ID: {}", clothId);
                            throw new EntityNotFoundException ("Cloth not found with id: " + clothId);
                        }
                );
    }

    private void validateLookAndClothIds(Long lookId, Long clothId) {
        if (lookId == null || clothId == null) {
            log.error("Invalid IDs: Look ID and Cloth ID must not be null");
            throw new IllegalArgumentException("Look ID and Cloth ID must not be null");
        }
    }
}
