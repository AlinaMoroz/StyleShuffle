package com.example.mobile_app.newslines.service;

import com.example.mobile_app.newslines.NewsLineRepository;
import com.example.mobile_app.newslines.dto.NewsLineCreateDto;
import com.example.mobile_app.newslines.dto.NewsLineReadDto;
import com.example.mobile_app.newslines.mapper.NewsLineMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class NewsLineService {

    private final NewsLineRepository newsLineRepository;
    private NewsLineMapper newsLineMapper;

    // TODO: 06.02.2025 addComment and getCommentsByNewsLineId methods

    public Optional<NewsLineReadDto> findById(Long id) {
        return newsLineRepository.findById(id)
                .map(newsLineMapper::toNewsLineReadDto);


    }

    @Transactional
    public NewsLineReadDto create(NewsLineCreateDto newsLineCreateDto) {
        return Optional.of(newsLineCreateDto)
                .map(newsLineMapper::toNewsLine)
                .map(newsLineRepository::save)
                .map(newsLineMapper::toNewsLineReadDto)
                .orElseThrow(() -> new IllegalArgumentException("NewsLineCreateDto cannot be null"));

    }

    public NewsLineReadDto addLike(Long id) {
        return newsLineRepository.findById(id)
                .map(newsLine -> {
                     newsLine.setLikeCount(newsLine.getLikeCount() + 1);
                     return newsLineRepository.save(newsLine);
                })
                .map(newsLineMapper::toNewsLineReadDto)
                .orElseThrow(() -> new IllegalArgumentException("NewsLine with id " + id + " not found"));
    }

    public NewsLineReadDto addDislike(Long id) {
        return newsLineRepository.findById(id)
                .map(newsLine -> {
                     newsLine.setDislikeCount(newsLine.getDislikeCount() + 1);
                     return newsLineRepository.save(newsLine);
                })
                .map(newsLineMapper::toNewsLineReadDto)
                .orElseThrow(() -> new IllegalArgumentException("NewsLine with id " + id + " not found"));
    }

    public boolean deleteById(Long id) {
        return newsLineRepository.findById(id)
                .map(newsLine -> {
                     newsLineRepository.delete(newsLine);
                     return true;
                 })
                .orElse(false);
    }

    public Page<NewsLineReadDto> findAllByOrderByLikeCountDesc(Pageable pageable) {
        return newsLineRepository.findAllByOrderByLikeCountDesc(pageable)
                .map(newsLineMapper::toNewsLineReadDto);
    }

    public Page<NewsLineReadDto> findAllByOrderByPostDateDesc(Pageable pageable) {
        return newsLineRepository.findAllByOrderByPostDateDesc(pageable)
                .map(newsLineMapper::toNewsLineReadDto);
    }
}


