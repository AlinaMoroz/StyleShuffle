package com.example.mobile_app.comments.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class CommentCreateDto {

    @NotNull
    @Positive
    Long userId;

    @NotNull
    @Max(512)
    String text;
    @PastOrPresent
    LocalDateTime datePost;

    @NotNull
    @Positive
    Long newsLineId;
}
