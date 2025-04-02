package com.example.mobile_app.looks.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import com.example.mobile_app.clothes.dto.ClothReadDto;
import com.example.mobile_app.clothes.mapper.ClothMapper;
import com.example.mobile_app.clothes.repository.ClothRepository;
import com.example.mobile_app.looks_clothes.LookCloth;
import com.example.mobile_app.looks.dto.LookCreateDto;
import com.example.mobile_app.looks.dto.LookUpdateDto;
import com.example.mobile_app.looks.dto.LookReadDto;
import com.example.mobile_app.looks.model.Look;
import com.example.mobile_app.users.mapper.UserMapper;
import com.example.mobile_app.users.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Slf4j
@Mapper(componentModel = "spring", uses = {ClothMapper.class, UserMapper.class})
public abstract class LookMapper {

    protected UserRepository userRepository;
    protected ClothRepository clothRepository;
    protected ClothMapper clothMapper;
    protected UserMapper userMapper;


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", expression = "java(userRepository.findById(lookCreateDto.getUserId()).get())")
    @Mapping(target = "lookClothes", ignore = true)
    public abstract Look toLook(LookCreateDto lookCreateDto);

    @Mapping(target = "userReadDto", expression = "java(userMapper.toUserReadDto(look.getUser()))")
    @Mapping(target = "clothReadDtos", expression = "java(mapLookClothesToClothReadDtos(look))")
    public abstract LookReadDto toLookReadDto(Look look);

    protected Set<ClothReadDto> mapLookClothesToClothReadDtos(Look look) {
        log.info("Mapping LookClothes to ClothReadDtos for Look with id {}", look.getId());
        return look.getLookClothes().stream()
                .map(LookCloth::getCloth)
                .map(clothMapper::toClothReadDto)
                .collect(Collectors.toSet());
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "lookClothes", ignore = true)
    public abstract void updateLook(LookUpdateDto lookUpdateDto, @MappingTarget Look look);

}
