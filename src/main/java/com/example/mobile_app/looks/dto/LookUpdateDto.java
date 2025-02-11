package com.example.mobile_app.looks.dto;

import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class LookUpdateDto {
    @Size(max = 255)
    String description;

    @Size(max = 128)
    String name;
}
