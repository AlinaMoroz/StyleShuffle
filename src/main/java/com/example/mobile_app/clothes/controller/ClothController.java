package com.example.mobile_app.clothes.controller;

import com.example.mobile_app.clothes.Type;
import com.example.mobile_app.clothes.dto.ClothCreateDto;
import com.example.mobile_app.clothes.dto.ClothReadDto;
import com.example.mobile_app.clothes.service.ClothService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/clothes")
public class ClothController {

    private final ClothService clothService;


    @GetMapping("/{id}")
    public ClothReadDto findById(@PathVariable Long id) {
        return clothService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/sets/{id}")
    public List<ClothReadDto> findAllClothBySetId(@PathVariable Long id){
        return clothService.findAllClothBySetId(id);
    }

    @PostMapping
    public ClothReadDto create(@RequestBody @Validated ClothCreateDto createDto){
        return clothService.create(createDto);
    }


    @GetMapping("/users/{id}")
    public List<ClothReadDto> findAllByUserIdAndType(@PathVariable Long id, @RequestParam Type type){
        return clothService.findAllByUserIdAndType(id, type);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        if(!clothService.deleteById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

}
