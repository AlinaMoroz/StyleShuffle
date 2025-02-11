package com.example.mobile_app.newslines.controller;

import com.example.mobile_app.newslines.dto.NewsLineCreateDto;
import com.example.mobile_app.newslines.dto.NewsLineReadDto;
import com.example.mobile_app.newslines.service.NewsLineService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/news-lines")
public class NewsLineController {

    // TODO: 06.02.2025 addComment and getCommentsByNewsLineId methods

    private final NewsLineService newsLineService;


    @GetMapping("/{id}")
    public NewsLineReadDto findById(Long id) {
        return newsLineService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "NewsLine not found"));
    }

    @GetMapping("/sortLikeCount")
    public Page<NewsLineReadDto> findAllByOrderedByLikeCount(Pageable pageable) {
        return newsLineService.findAllByOrderByLikeCountDesc(pageable);
    }

    @GetMapping("/sortPostDate")
    public Page<NewsLineReadDto> findAllByOrderedByPostDateDesc(Pageable pageable){
        return newsLineService.findAllByOrderByPostDateDesc(pageable);

    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewsLineReadDto create(@RequestBody NewsLineCreateDto newsLineCreateDto) {
        return newsLineService.create(newsLineCreateDto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        newsLineService.deleteById(id);
    }


    @PatchMapping("/{id}/like")
    public NewsLineReadDto addLike(@PathVariable Long id) {
        return newsLineService.addLike(id);
    }

    @PatchMapping("/{id}/dislike")
    public NewsLineReadDto addDislike(@PathVariable Long id) {
        return newsLineService.addDislike(id);
    }


//    @PostMapping("/{id}/comments")
//    public NewsLineDto addComment(@PathVariable Long id, @RequestBody CreateCommentDto commentDto) {
//        return newsLineService.addComment(id, commentDto);
//    }
//@GetMapping("/{id}/comments")
//public List<CommentDto> getCommentsByNewsLineId(@PathVariable Long id) {
//    return newsLineService.getCommentsByNewsLineId(id);
//}

}
