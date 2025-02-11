package com.example.mobile_app.looks.mapper;

import com.example.mobile_app.newslines.NewsLineRepository;
import com.example.mobile_app.newslines.mapper.NewsLineMapper;
import com.example.mobile_app.looks.dto.LookCreateDto;
import com.example.mobile_app.looks.dto.LookUpdateDto;
import com.example.mobile_app.looks.dto.LookReadDto;
import com.example.mobile_app.looks.model.Look;
import com.example.mobile_app.users.mapper.UserMapper;
import com.example.mobile_app.users.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class LookMapper {

    protected NewsLineRepository newsLineRepository;
    protected UserRepository userRepository;

    protected NewsLineMapper newsLineMapper;
    protected UserMapper userMapper;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "newsLine", expression = "java(newsLineRepository.findById(lookCreateDto.getNewsLineId()).get())")
    @Mapping(target = "user", expression = "java(userRepository.findById(lookCreateDto.getUserId()).get())")
    @Mapping(target = "lookClothes", ignore = true)
    public abstract Look toLook(LookCreateDto lookCreateDto);

    @Mapping(target = "newsLineReadDto", expression = "java(newsLineMapper.toNewsLineReadDto(look.getNewsLine()))")
    @Mapping(target = "userReadDto", expression = "java(userMapper.toUserReadDto(look.getUser()))")
    public abstract LookReadDto toLookReadDto(Look look);

    @Mapping(target = "newsLine", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "lookClothes", ignore = true)
    @Mapping(target = "id", ignore = true)
    public abstract void updateLook(LookUpdateDto lookUpdateDto, @MappingTarget Look look);
}
