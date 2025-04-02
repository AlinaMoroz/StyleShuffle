package com.example.mobile_app.looks.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;


import java.util.Set;

@Value
@Schema(description = "DTO for creating a Look entity")
public class LookCreateDto {

    @Schema(description = "Description of the look", example = "A beautiful black dress")
    @Size(max = 255)
    String description;

    @Schema(description = "Name of the look", example = "Summer Collection")
    @Size(max = 128)
    String name;

    @Schema(description = "ID of the user creating the look",example = "123")
    @NotNull
    @Min(0)
    Long userId;

    @Schema(description = "Set of IDs of the clothes associated with the look",example = "[1, 2, 3]")
    @NotNull
    @Min(1)
    Set<Long> clothIds;
}
