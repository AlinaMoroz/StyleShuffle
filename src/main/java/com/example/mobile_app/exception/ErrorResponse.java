package com.example.mobile_app.exception;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Schema(description = "Error response object")
public class ErrorResponse {

    @Schema(description = "Error message")
    private final Map<String, String> errors = new HashMap<>();

    @Schema(description = "Response status code")
    private int status;
}
