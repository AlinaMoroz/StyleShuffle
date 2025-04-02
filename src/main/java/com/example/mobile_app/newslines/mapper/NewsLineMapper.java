package com.example.mobile_app.newslines.mapper;

import com.example.mobile_app.looks.LookRepository;
import com.example.mobile_app.looks.mapper.LookMapper;
import com.example.mobile_app.newslines.dto.NewsLineCreateDto;
import com.example.mobile_app.newslines.dto.NewsLineReadDto;
import com.example.mobile_app.newslines.model.NewsLine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class  NewsLineMapper {
    protected LookMapper lookMapper;
    protected LookRepository lookRepository;

    @Mapping(target = "lookReadDto", expression = "java(lookMapper.toLookReadDto(newsLine.getLook()))")
    public abstract NewsLineReadDto toNewsLineReadDto(NewsLine newsLine);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "look", expression = "java(lookRepository.findById(newsLineCreateDto.getLookId()).get())")
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "likeCount", ignore = true)
    @Mapping(target = "dislikeCount", ignore = true)
    public abstract NewsLine toNewsLine(NewsLineCreateDto newsLineCreateDto);
}
