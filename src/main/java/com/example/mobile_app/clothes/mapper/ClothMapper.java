package com.example.mobile_app.clothes.mapper;

import com.example.mobile_app.clothes.dto.ClothCreateDto;
import com.example.mobile_app.clothes.dto.ClothReadDto;
import com.example.mobile_app.clothes.model.Cloth;
import com.example.mobile_app.users.mapper.UserMapper;
import com.example.mobile_app.users.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class ClothMapper {
    protected UserRepository userRepository;
    protected UserMapper userMapper;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", expression = "java(userRepository.findById(dto.getUserId()).get())")
    @Mapping(target = "brand", ignore = true)
    @Mapping(target = "color", ignore = true)
    @Mapping(target = "lookClothes", ignore = true)
    public abstract Cloth toCloth(ClothCreateDto dto);

    @Mapping(target = "userReadDto", expression = "java(userMapper.toUserReadDto(cloth.getUser()))")
    public abstract ClothReadDto toClothReadDto(Cloth cloth);




}
