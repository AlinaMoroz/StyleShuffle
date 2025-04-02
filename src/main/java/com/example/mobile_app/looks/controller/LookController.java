package com.example.mobile_app.looks.controller;

import com.example.mobile_app.exception.EntityNotFoundException;
import com.example.mobile_app.looks.dto.LookReadDto;
import com.example.mobile_app.looks.dto.LookUpdateDto;
import com.example.mobile_app.looks.dto.LookCreateDto;
import com.example.mobile_app.looks.service.LookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/looks")
@Slf4j
public class LookController {

    private final LookService lookService;


    @Operation(summary = "Get Look by ID", description = "Returns a Look entity by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Look found"),
            @ApiResponse(responseCode = "404", description = "Look not found")
    })
    @GetMapping("/{id}")
    public LookReadDto findById(@PathVariable Long id) {
        log.info("Fetching Look with ID: {}", id);
        return lookService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Look not found with id: " + id));
    }

    @Operation(summary = "Get Looks by User ID", description = "Returns a paginated list of Looks created by the specified user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Looks retrieved successfully")
    })
    @GetMapping("/users/{id}")
    public Page<LookReadDto> findAllByUserId(@PathVariable Long id, Pageable pageable){
        log.info("Fetching Looks for user with ID: {}, page: {}", id, pageable.getPageNumber());
        return lookService.findAllByUserId(id, pageable);
    }

    @Operation(summary = "Update Look", description = "Updates an existing Look using provided data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Look updated successfully"),
            @ApiResponse(responseCode = "404", description = "Look not found")
    })
    @PutMapping("/{id}")
    public LookReadDto update(@PathVariable Long id, @Valid @RequestBody LookUpdateDto lookUpdateDto){
        log.info("Attempting to update Look with ID: {} using DTO: {}", id, lookUpdateDto);
        return lookService.update(id, lookUpdateDto)
                .orElseThrow(() -> new EntityNotFoundException("Look not found with id: " + id));

    }

    @Operation(summary = "Delete Look", description = "Deletes the Look entity with the specified ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Look deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Look not found")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        log.info("Attempting to delete Look with ID: {}", id);
        if(!lookService.deleteById(id)){
            log.warn("Look with ID {} not found for deletion", id);
            throw new EntityNotFoundException("Look not found with id: " + id);
        }
    }

    @Operation(summary = "Create Look", description = "Creates a new Look using provided data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Look created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public LookReadDto create(@Validated @RequestBody LookCreateDto lookCreateDto) {
        log.info("Creating new Look with DTO: {}", lookCreateDto);
        return lookService.create(lookCreateDto);
    }

    @Operation(summary = "Add Cloth to Look", description = "Adds a Cloth to an existing Look")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cloth added successfully"),
            @ApiResponse(responseCode = "404", description = "Look or Cloth not found")
    })
    @PatchMapping ("/{lookId}/clothes/{clothId}")
    public void addClothToLook(@PathVariable Long lookId, @PathVariable Long clothId){
        log.info("Attempting to add Cloth with ID: {} to Look with ID: {}", clothId, lookId);
        lookService.addClothToLook(lookId, clothId);
    }

    @Operation(summary = "Remove Cloth from Look", description = "Removes a Cloth from an existing Look")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cloth removed successfully"),
            @ApiResponse(responseCode = "404", description = "Look or Cloth not found")
    })
    @DeleteMapping ("/{lookId}/clothes/{clothId}")
    public void removeClothFromLook(@PathVariable Long lookId, @PathVariable Long clothId){
        log.info("Attempting to remove Cloth with ID: {} from Look with ID: {}", clothId, lookId);
        lookService.removeClothFromLook(lookId, clothId);
    }

}
