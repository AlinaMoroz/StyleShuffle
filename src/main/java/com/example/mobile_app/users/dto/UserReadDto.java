package com.example.mobile_app.users.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder

@Schema(description = "Entity user")
public record UserReadDto(@Schema(description = "id user")
                          Long id,
                          @Schema(description = "User name")
                          String name,
                          @Schema(description = "User email")
                          String email,
                          @Schema(description = "User avatar")
                          String avatar,
                          @Schema(description = "User size cloth")
                          String size,
                          @Schema(description = "User surname")
                          String surname,
                          @Schema(description = "User nickname")
                          String username) {
}
