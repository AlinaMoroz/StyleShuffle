package com.example.mobile_app.looks.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.util.Set;

@Value
@Schema(description = "DTO for updating a Look entity")
public class LookUpdateDto {
    @Schema(description = "Update description of the Look")
    @Size(max = 255)
    String description;

    @Schema(description = "Update name of the Look")
    @Size(max = 128)
    String name;
}
