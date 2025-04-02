package com.example.mobile_app.comments.service;

import com.example.mobile_app.comments.dto.CommentCreateDto;
import com.example.mobile_app.comments.dto.CommentReadDto;
import com.example.mobile_app.comments.dto.CommentUpdateDto;
import com.example.mobile_app.comments.mapper.CommentMapper;
import com.example.mobile_app.comments.repository.CommentRepository;
import com.example.mobile_app.exception.EntityCreationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CommentService {


    private final CommentRepository commentRepository;
    private CommentMapper commentMapper;

    public Optional<CommentReadDto> findById(Long id) {
        Optional<CommentReadDto> result = commentRepository.findById(id)
                .map(commentMapper::toCommentReadDto);
        if (result.isEmpty()) {
            log.warn("No comment found for ID: {}", id);
        } else {
            log.info("Comment found for ID: {}", id);
        }
        return result;
    }

    public List<CommentReadDto> findAllByNewsLineId(Long newsLineId) {
        List<CommentReadDto> result = commentRepository.findAllByNewsLineId(newsLineId)
                .stream()
                .map(commentMapper::toCommentReadDto)
                .toList();
        log.info("Found {} comments for news line ID: {}", result.size(), newsLineId);
        return result;
    }


    @Transactional
    public Optional<CommentReadDto> update(Long id, CommentUpdateDto commentUpdateDto) {
        Optional<CommentReadDto> result = commentRepository.findById(id)
                .map(entity -> {
                    commentMapper.updateComment(commentUpdateDto, entity);
                    commentRepository.save(entity);
                    CommentReadDto updatedComment = commentMapper.toCommentReadDto(entity);
                    log.info("Successfully updated comment with ID: {}", id);
                    return updatedComment;
                });
        if (result.isEmpty()) {
            log.warn("Update failed. No comment found with ID: {}", id);
        }
        return result;
    }

    @Transactional
    public CommentReadDto create(CommentCreateDto createDto) {
        CommentReadDto result = Optional.of(createDto)
                .map(commentMapper::toComment)
                .map(commentRepository::save)
                .map(commentMapper::toCommentReadDto)
                .orElseThrow(() -> {
                    log.error("Comment creation failed for DTO: {}", createDto);
                    return new EntityCreationException("Failed to create comment");
                });
        log.info("Successfully created comment with ID: {}", result.id());
        return result;
    }

    @Transactional
    public Boolean deleteById(Long id) {
        Boolean deleted = commentRepository.findById(id)
                .map(entity -> {
                    commentRepository.delete(entity);
                    commentRepository.flush();
                    log.info("Successfully deleted comment with ID: {}", id);
                    return true;
                }).orElse(false);
        if (!deleted) {
            log.warn("Deletion failed. No comment found with ID: {}", id);
        }
        return deleted;
    }
}
