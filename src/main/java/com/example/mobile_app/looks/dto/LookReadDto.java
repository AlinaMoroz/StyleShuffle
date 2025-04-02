package com.example.mobile_app.looks.dto;

import com.example.mobile_app.clothes.dto.ClothReadDto;
import com.example.mobile_app.users.dto.UserReadDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.Set;

@Builder
@Schema(description = "DTO for reading a Look entity")
public record LookReadDto(
        @Schema(description = "Unique identifier of the Look")
        Long id,
        @Schema(description = "Description of the Look")
        String description,
        @Schema(description = "Name of the Look")
        String name,
        @Schema(description = "Details of the user who created the Look")
        UserReadDto userReadDto,
        @Schema(description = "Set of clothes associated with the Look")
        Set<ClothReadDto> clothReadDtos) {

}
