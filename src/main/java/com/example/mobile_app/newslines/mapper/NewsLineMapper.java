package com.example.mobile_app.newslines.mapper;

import com.example.mobile_app.newslines.dto.NewsLineCreateDto;
import com.example.mobile_app.newslines.dto.NewsLineReadDto;
import com.example.mobile_app.newslines.model.NewsLine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NewsLineMapper {

    NewsLineReadDto toNewsLineReadDto(NewsLine newsLine);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "looks", ignore = true)
    @Mapping(target = "likeCount", ignore = true)
    @Mapping(target = "dislikeCount", ignore = true)
    NewsLine toNewsLine(NewsLineCreateDto newsLineCreateDto);
}
