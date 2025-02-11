package com.example.mobile_app.users.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
@Schema(description = "Request to update user data")
public class UserUpdateDto {


    @Schema(description = "User name")
    @Size(min = 2, max = 128, message = "Value must be between 2 and 128 characters ")
    @NotBlank(message = "User name cannot be blank")
    String name;


    @Schema(description = "User photo")
    @Size(max = 255)
    String avatar;

    @Schema(description = "Clothing size")
    @Size(max = 16)
    String size;

    @Schema(description = "User surname")
    @Size(min = 2, max = 128, message = "Value must be between 2 and 128 characters ")
    @NotBlank(message = "User surname cannot be blank")
    String surname;

    @Schema(description = "Nickname user")
    @NotBlank(message = "Nickname must be between 2 and 128 characters")
    @Size(min = 2, max = 128)
    String username;
}
