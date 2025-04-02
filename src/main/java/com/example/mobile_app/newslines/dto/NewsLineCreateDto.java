package com.example.mobile_app.newslines.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Schema(description = "DTO for creating a news line entry")
public class NewsLineCreateDto {
    @Schema(description = "ID of the look associated with the news line")
    @NotNull(message = "lookId must not be null")
    @Positive(message = "lookId must be a positive number")
    Long lookId;

    @Schema(
            description = "Publication date for the news line. Must be the current or a future date.",
            example = "2023-12-31T12:00:00"
    )
    @NotNull(message = "Publication date must not be null")
    @FutureOrPresent(message = "Publication date must be in the present or future")
    LocalDateTime postDate;

}
