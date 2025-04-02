package com.example.mobile_app.looks.service;

import com.example.mobile_app.clothes.model.Cloth;
import com.example.mobile_app.clothes.repository.ClothRepository;
import com.example.mobile_app.looks.LookRepository;
import com.example.mobile_app.looks.dto.LookCreateDto;
import com.example.mobile_app.looks.dto.LookReadDto;
import com.example.mobile_app.looks.dto.LookUpdateDto;
import com.example.mobile_app.looks.mapper.LookMapper;
import com.example.mobile_app.looks_clothes.LookClothRepository;
import lombok.RequiredArgsConstructor;
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
public class SetService {


    private final LookRepository lookRepository;
    private LookMapper lookMapper;
    private final ClothRepository clothRepository;
    private final LookClothRepository lookClothRepository;

    public Optional<LookReadDto> findById(Long id) {
        return lookRepository.findById(id)
                .map(lookMapper::toLookReadDto);
    }

    public Optional<LookReadDto> findByNewsLineId(Long id) {
        return lookRepository.findByNewsLineId(id)
                .map(lookMapper::toLookReadDto);
    }


    public Page<LookReadDto> findAllByUserId(Long userId, Pageable pageable) {
        return lookRepository.findAllByUserId(userId, pageable)
                .map(lookMapper::toLookReadDto);
    }


    @Transactional
    public Optional<LookReadDto> update(Long id, LookUpdateDto lookUpdateDto) {
        return lookRepository.findById(id)
                .map(entity -> {
                    lookMapper.updateLook(lookUpdateDto, entity);
                    lookRepository.save(entity);
                    return lookMapper.toLookReadDto(entity);
                });
    }

    @Transactional
    public boolean deleteById(Long id) {
        return lookRepository.findById(id)
                .map(entity -> {
                    lookRepository.delete(entity);
                    lookRepository.flush();
                    return true;
                }).orElse(false);


    }

    @Transactional
    public LookReadDto create(LookCreateDto lookCreateDto) {
        return Optional.ofNullable(lookCreateDto)
                .map(dto ->{
                    var clothSet = dto.getClothIds().stream()
                            .map(clothId -> clothRepository.findById(clothId)
                                    .orElseThrow(() -> new IllegalArgumentException("Cloth not found with id: " + clothId)))
                            .collect(Collectors.toSet());
                    return lookMapper.toLook(dto, clothSet);
                })
                .map(lookRepository::save)
                .map(lookMapper::toLookReadDto)
                .orElseThrow(() -> new IllegalArgumentException("Invalid set data"));
    }

//        @Transactional
//    public Optional<SetReadDto> addClothToSet(Long setId, Long clothId) {
//        return setRepository.findById(setId)
//                .flatMap(set ->{
//                         Optional<Cloth> cloth = clothRepository.findById(clothId);
//                         if(cloth.isPresent()){
//                             SetCloth setCloth = SetCloth.builder().cloth(cloth.get()) .set(set) .build();
//                         }
//                        }
//
//
//                );
//        }
//    }


}
