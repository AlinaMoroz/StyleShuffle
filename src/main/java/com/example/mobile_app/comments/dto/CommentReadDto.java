package com.example.mobile_app.comments.dto;

import com.example.mobile_app.users.dto.UserReadDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;


import java.time.LocalDateTime;

@Builder
@Schema(description = "DTO for reading a comment")
public record CommentReadDto(
        @Schema(description = "Unique identifier of the comment")
        Long id,
        @Schema(description = "Details of the user who posted the comment")
        UserReadDto userReadDto,
        @Schema(description = "Content of the comment")
        String text,
        @Schema(description = "Date and time when the comment was posted")
        LocalDateTime datePost) {
}
