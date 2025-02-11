package com.example.mobile_app.looks.dto;

import com.example.mobile_app.newslines.dto.NewsLineReadDto;
import com.example.mobile_app.users.dto.UserReadDto;
import lombok.Builder;

@Builder
public record LookReadDto(Long id,

                          String description,

                          NewsLineReadDto newsLineReadDto,

                          String name,

                          UserReadDto userReadDto) {

}
