package com.example.mobile_app.comments.controller;

import com.example.mobile_app.comments.service.CommentService;
import com.example.mobile_app.comments.dto.CommentCreateDto;
import com.example.mobile_app.comments.dto.CommentReadDto;
import com.example.mobile_app.comments.dto.CommentUpdateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
@Slf4j
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "Get comment by ID", description = "Returns a comment by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment found"),
            @ApiResponse(responseCode = "404", description = "Comment not found")
    })
    @GetMapping("/{id}")
    public CommentReadDto findById(@PathVariable Long id) {
        log.info("Fetching comment by ID: {}", id);
        return commentService.findById(id)
                .orElseThrow(() -> {
                    log.warn("Comment with ID {} not found", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found");
                });
    }

    @Operation(summary = "Get all comments for a news line", description = "Returns a list of comments associated with the specified news line ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comments retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "News line not found")
    })
    @GetMapping("/newsLines/{id}")
    public List<CommentReadDto> findAllByNewsLineId(@PathVariable Long id) {
        log.info("Fetching comments for news line ID: {}", id);
        return commentService.findAllByNewsLineId(id);
    }

    @Operation(summary = "Update comment", description = "Updates an existing comment with the provided data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment updated successfully"),
            @ApiResponse(responseCode = "404", description = "Comment not found")
    })
    @PutMapping("/{id}")
    public CommentReadDto update(@PathVariable Long id, @RequestBody @Validated CommentUpdateDto commentUpdateDto) {
        log.info("Attempting to update comment with ID: {} using DTO: {}", id, commentUpdateDto);
        return commentService.update(id, commentUpdateDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Create new comment", description = "Creates a new comment using the provided data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comment created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentReadDto create(@RequestBody @Validated CommentCreateDto commentCreateDto) {
        log.info("Creating new comment with DTO: {}", commentCreateDto);
        return commentService.create(commentCreateDto);

    }

    @Operation(summary = "Delete comment", description = "Deletes a comment by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Comment deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Comment not found")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        log.info("Attempting to delete comment with ID: {}", id);
        if (!commentService.deleteById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found");
        }
    }
}
