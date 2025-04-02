package com.example.mobile_app.comments.mapper;

import com.example.mobile_app.comments.dto.CommentCreateDto;
import com.example.mobile_app.comments.dto.CommentReadDto;
import com.example.mobile_app.comments.dto.CommentUpdateDto;
import com.example.mobile_app.comments.modal.Comment;
import com.example.mobile_app.newslines.NewsLineRepository;
import com.example.mobile_app.users.mapper.UserMapper;
import com.example.mobile_app.users.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class CommentMapper {

    protected UserRepository userRepository;
    protected NewsLineRepository newsLineRepository;
    protected UserMapper userMapper;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", expression = "java(userRepository.findById(commentDto.getUserId()).get())")
    @Mapping(target = "newsLine", expression = "java(newsLineRepository.findById(commentDto.getNewsLineId()).get())")
    public abstract Comment toComment(CommentCreateDto commentDto);

    @Mapping(target = "userReadDto", expression = "java(userMapper.toUserReadDto(comment.getUser()))")
    public abstract CommentReadDto toCommentReadDto(Comment comment);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "newsLine", ignore = true)
    @Mapping(target = "datePost", ignore = true)
    public abstract void updateComment(CommentUpdateDto commentDto, @MappingTarget Comment comment);
}
