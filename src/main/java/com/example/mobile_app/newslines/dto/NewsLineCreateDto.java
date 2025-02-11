package com.example.mobile_app.newslines.dto;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class NewsLineCreateDto {
    LocalDateTime postDate;
}
