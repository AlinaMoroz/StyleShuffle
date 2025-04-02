package com.example.mobile_app.comments;

import com.example.mobile_app.comments.dto.CommentCreateDto;
import com.example.mobile_app.comments.dto.CommentReadDto;
import com.example.mobile_app.comments.dto.CommentUpdateDto;
import com.example.mobile_app.comments.mapper.CommentMapper;
import com.example.mobile_app.comments.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {


    private final CommentRepository commentRepository;
    private CommentMapper commentMapper;


    public Optional<CommentReadDto> findById(Long id) {
        return commentRepository.findById(id)
                .map(commentMapper::toCommentReadDto);
    }

    public List<CommentReadDto> findAllByNewsLineId(Long newsLineId) {

        return commentRepository.findAllByNewsLineId(newsLineId)
                .stream()
                .map(commentMapper::toCommentReadDto)
                .toList();

    }


    @Transactional
    public Optional<CommentReadDto> update(Long id, CommentUpdateDto commentUpdateDto) {
        return commentRepository.findById(id)
                .map(entity -> {
                    commentMapper.updateComment(commentUpdateDto, entity);
                    commentRepository.save(entity);
                    return commentMapper.toCommentReadDto(entity);
                });
    }

    @Transactional
    public CommentReadDto create(CommentCreateDto createDto) {
        return Optional.of(createDto)
                .map(commentMapper::toComment)
                .map(commentRepository::save)
                .map(commentMapper::toCommentReadDto)
                .orElseThrow(() -> new IllegalArgumentException("Invalid input data"));
    }

    @Transactional
    public Boolean deleteById(Long id) {
        return commentRepository.findById(id)
                .map(entity -> {
                    commentRepository.delete(entity);
                    commentRepository.flush();
                    return true;
                }).orElse(false);
    }


}
