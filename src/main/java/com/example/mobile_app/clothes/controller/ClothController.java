package com.example.mobile_app.clothes.controller;

import com.example.mobile_app.clothes.Type;
import com.example.mobile_app.clothes.dto.ClothCreateDto;
import com.example.mobile_app.clothes.dto.ClothReadDto;
import com.example.mobile_app.clothes.service.ClothService;
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
@RequestMapping("/api/v1/clothes")
@Slf4j
public class ClothController {

    private final ClothService clothService;

    @Operation(summary = "Get cloth by ID", description = "Returns a cloth by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cloth found"),
            @ApiResponse(responseCode = "404", description = "Cloth not found")
    })
    @GetMapping("/{id}")
    public ClothReadDto findById(@PathVariable Long id) {
        log.info("Fetching cloth with ID: {}", id);
        return clothService.findById(id)
                .orElseThrow(() -> {
                    log.warn("Cloth with ID {} not found", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Cloth not found");
                });
    }

    @Operation(summary = "Get clothes by look ID", description = "Returns list of clothes for a given look ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of clothes returned")
    })
    @GetMapping("/looks/{id}")
    public List<ClothReadDto> findAllClothByLookId(@PathVariable Long id) {
        log.info("Fetching clothes for look ID: {}", id);
        return clothService.findAllClothByLookId(id);
    }

    @Operation(summary = "Create new cloth", description = "Creates a new cloth based on provided data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cloth successfully created"),
            @ApiResponse(responseCode = "400", description = "Creation failed due to invalid data")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClothReadDto create(@RequestBody @Validated ClothCreateDto createDto) {
        log.info("Creating new cloth with data: {}", createDto);
        return clothService.create(createDto);
    }

    @Operation(summary = "Get clothes by user ID and type", description = "Returns list of clothes for a given user ID filtered by type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of clothes returned")
    })
    @GetMapping("/users/{id}")
    public List<ClothReadDto> findAllByUserIdAndType(@PathVariable Long id, @RequestParam Type type) {
        log.info("Fetching clothes for user ID: {} with type: {}", id, type);
        return clothService.findAllByUserIdAndType(id, type);
    }

    @Operation(summary = "Delete cloth", description = "Deletes a cloth by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cloth successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Cloth not found")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        log.info("Attempting to delete cloth with ID: {}", id);
        if (!clothService.deleteById(id)) {
            log.warn("Deletion failed. Cloth with ID {} not found", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cloth not found");
        }
        log.info("Cloth with ID {} successfully deleted", id);
    }

}
