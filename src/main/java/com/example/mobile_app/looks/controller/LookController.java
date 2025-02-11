package com.example.mobile_app.looks.controller;

import com.example.mobile_app.looks.dto.LookReadDto;
import com.example.mobile_app.looks.dto.LookUpdateDto;
import com.example.mobile_app.looks.dto.LookCreateDto;
import com.example.mobile_app.looks.service.SetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sets")
public class LookController {

    private final SetService setService;
    // TODO: 07.02.2025 to do addClothToSet

    @GetMapping("/{id}")
    public LookReadDto findById(@PathVariable Long id) {
        return setService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Set not found"));
    }

    @GetMapping("/newsLines/{id}")
    public LookReadDto findByNewsLineId(@PathVariable Long id){
        return setService.findByNewsLineId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Set not found"));
    }
    @GetMapping("/users/{id}")
    public Page<LookReadDto> findAllByUserId(@PathVariable Long id, Pageable pageable){
        return setService.findAllByUserId(id, pageable);
    }


    @PutMapping("/{id}")
    public LookReadDto update(@PathVariable Long id, @Validated @RequestBody LookUpdateDto lookUpdateDto){
        return setService.update(id, lookUpdateDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        if(!setService.deleteById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    public LookReadDto create(@Validated @RequestBody LookCreateDto lookCreateDto) {
        return setService.create(lookCreateDto);
    }


//    @PostMapping("/sets/{setId}/clothes/{clothId}")
//    public SetReadDto addClothToSet(@PathVariable Long setId, @PathVariable Long clothId){
//        return setService.addClothToSet(setId, clothId)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//
//    }

}
