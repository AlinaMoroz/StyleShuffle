package com.example.mobile_app.newslines.dto;

import com.example.mobile_app.looks.dto.LookReadDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@Schema(description = "DTO for reading a news line entry")
public record NewsLineReadDto(
        @Schema(description = "Unique identifier of the news line")
        Long id,
        @Schema(description = "Details of the associated look")
        LookReadDto lookReadDto,
        @Schema(description = "Publication date of the news line")
        LocalDateTime postDate,
        @Schema(description = "Number of likes for the news line")
        Integer likeCount,
        @Schema(description = "Number of dislikes for the news line")
        Integer dislikeCount) {
}
