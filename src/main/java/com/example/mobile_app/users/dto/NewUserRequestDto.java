package com.example.mobile_app.users.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
@Schema(description = "Registration new user")
public class NewUserRequestDto {



    @Schema(description = "Email user")
    @Email(message = "Provide a valid email address")
    @NotBlank(message = "Email cannot be blank")
    String email;

    @Schema(description = "Password user")
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, max = 255, message = "Password must contain more than 6 characters")
    String password;

    @Schema(description = "Nickname user")
    @NotBlank(message = "Nickname must be between 2 and 128 characters")
    @Size(min = 2, max = 128)
    String username;
}
