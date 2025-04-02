package com.example.mobile_app.users.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "Response on registration user ")
public record NewUserResponseDto(@Schema(description = "Id user")
                                 Long id,
                                 @Schema(description = "Nickname user")
                                 String username,
                                 @Schema(description = "Email user")
                                 String email) {
}
