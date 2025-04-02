package com.example.mobile_app.comments.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Schema(description = "DTO for creating a comment")
public class CommentCreateDto {

    @Schema(description = "ID of the user creating the comment", example = "1")
    @NotNull
    @Positive
    Long userId;

    @Schema(description = "Text content of the comment", example = "This is a comment.", maxLength = 512)
    @NotNull
    @Max(512)
    String text;

    @Schema(description = "Date and time when the comment was posted", example = "2021-12-31T23:59:59")
    @PastOrPresent
    LocalDateTime datePost;

    @Schema(description = "ID of the news line associated with the comment", example = "10")
    @NotNull
    @Positive
    Long newsLineId;
}
