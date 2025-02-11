package com.example.mobile_app.clothes.dto;


import com.example.mobile_app.clothes.Type;
import com.example.mobile_app.clothes.Season;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Value;

@Value
@Schema(description = "Create new cloth")
public class ClothCreateDto {
    @Schema(description = "User id")
    @NotNull(message = "User id cannot be null")
    @Positive(message = "User id need to be positive")
    Long userId;

    @Schema(description = "Link to photo")
    @NotBlank(message = "Link to photo cannot be blank")
    @Size(max = 255)
    String linkPhoto;

    @Schema(description = "Season of cloth")
    @NotBlank(message = "Season of cloth cannot be blank")
    Season season;

    @Schema(description = "Type of cloth")
    @NotBlank(message = "Type of cloth cannot be blank")
    Type type;
}
