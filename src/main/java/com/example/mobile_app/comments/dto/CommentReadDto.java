package com.example.mobile_app.comments.dto;

import com.example.mobile_app.users.dto.UserReadDto;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Builder
public record CommentReadDto(Long id,

        UserReadDto userReadDto,

        String text,

        LocalDateTime datePost) {


}
