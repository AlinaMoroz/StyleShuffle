package com.example.mobile_app.newslines.controller;

import com.example.mobile_app.newslines.dto.NewsLineCreateDto;
import com.example.mobile_app.newslines.dto.NewsLineReadDto;
import com.example.mobile_app.newslines.service.NewsLineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/news-lines")
@Slf4j
public class NewsLineController {


    private final NewsLineService newsLineService;

    @Operation(summary = "Get NewsLine by ID",
            description = "Returns a NewsLine by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "NewsLine found"),
            @ApiResponse(responseCode = "404", description = "NewsLine not found")
    })
    @GetMapping("/{id}")
    public NewsLineReadDto findById(Long id) {
        log.info("Fetching NewsLine with ID: {}", id);
        return newsLineService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "NewsLine not found"));
    }

    @Operation(summary = "Get all NewsLines sorted by like count",
            description = "Returns paginated NewsLines sorted in descending order by like count")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "NewsLines retrieved successfully")
    })
    @GetMapping("/sortLikeCount")
    public Page<NewsLineReadDto> findAllByOrderedByLikeCountDesc(Pageable pageable) {
        log.info("Fetching NewsLines ordered by like count descending, page: {}", pageable.getPageNumber());
        return newsLineService.findAllByOrderByLikeCountDesc(pageable);
    }

    @Operation(summary = "Get all NewsLines sorted by post date",
            description = "Returns paginated NewsLines sorted in descending order by post date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "NewsLines retrieved successfully")
    })
    @GetMapping("/sortPostDate")
    public Page<NewsLineReadDto> findAllByOrderedByPostDateDesc(Pageable pageable) {
        log.info("Fetching NewsLines ordered by post date descending, page: {}", pageable.getPageNumber());
        return newsLineService.findAllByOrderByPostDateDesc(pageable);

    }

    @Operation(summary = "Create a new NewsLine",
            description = "Creates a new NewsLine from the provided data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "NewsLine created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewsLineReadDto create(@Valid @RequestBody NewsLineCreateDto newsLineCreateDto) {
        log.info("Creating NewsLine with DTO: {}", newsLineCreateDto);
        return newsLineService.create(newsLineCreateDto);
    }

    @Operation(summary = "Delete NewsLine by ID",
            description = "Deletes the NewsLine with the specified ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "NewsLine deleted successfully"),
            @ApiResponse(responseCode = "404", description = "NewsLine not found")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        log.info("Attempting to delete NewsLine with ID: {}", id);
        newsLineService.deleteById(id);
    }

    @Operation(summary = "Add like to NewsLine",
            description = "Increments the like count for the NewsLine with the specified ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Like added successfully"),
            @ApiResponse(responseCode = "404", description = "NewsLine not found")
    })
    @PatchMapping("/{id}/like")
    public NewsLineReadDto addLike(@PathVariable Long id) {
        log.info("Adding like to NewsLine with ID: {}", id);
        return newsLineService.addLike(id);
    }

    @Operation(summary = "Add dislike to NewsLine",
            description = "Increments the dislike count for the NewsLine with the specified ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dislike added successfully"),
            @ApiResponse(responseCode = "404", description = "NewsLine not found")
    })
    @PatchMapping("/{id}/dislike")
    public NewsLineReadDto addDislike(@PathVariable Long id) {
        log.info("Adding dislike to NewsLine with ID: {}", id);
        return newsLineService.addDislike(id);
    }

}
