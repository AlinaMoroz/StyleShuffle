package com.example.mobile_app.looks.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class LookCreateDto {
    @Size(max = 255)
    String description;

    Long newsLineId;

    @Size(max = 128)
    String name;

    @NotNull
    @Min(0)
    Long userId;
}
