package com.example.mobile_app.newslines.dto;

import lombok.Builder;

import java.time.LocalDateTime;
@Builder
public record NewsLineReadDto(Long id,
                              LocalDateTime postDate,
                              Integer likeCount,
                              Integer dislikeCount) {
}
