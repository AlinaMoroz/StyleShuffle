package com.example.mobile_app.comments.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
@Schema(description = "DTO for updating a comment")
public class CommentUpdateDto {
    @Schema(
            description = "Updated text content of the comment",
            example = "Updated comment text",
            maxLength = 512
    )
    @NotNull
    @Size(max = 512)
    String text;
}
