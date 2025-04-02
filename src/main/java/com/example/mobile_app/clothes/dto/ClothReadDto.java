package com.example.mobile_app.clothes.dto;

import com.example.mobile_app.clothes.Type;
import com.example.mobile_app.clothes.Season;
import com.example.mobile_app.users.dto.UserReadDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "Read dto for a cloth")
public record ClothReadDto(
        @Schema(description = "Id of the cloth")
        Long id,
        @Schema(description = "URL to the photo of the cloth")
        String linkPhoto,
        @Schema(description = "Season of the cloth")
        Season season,
        @Schema(description = "Type of the cloth")
        Type type) {

}
