package com.example.mobile_app.newslines.service;

import com.example.mobile_app.exception.EntityCreationException;
import com.example.mobile_app.exception.EntityNotFoundException;
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
    private final NewsLineMapper newsLineMapper;

    public Optional<NewsLineReadDto> findById(Long id) {
        Optional<NewsLineReadDto> result = newsLineRepository.findById(id)
                .map(newsLineMapper::toNewsLineReadDto);
        if (result.isPresent()) {
            log.info("NewsLine with ID {} found", id);
        } else {
            log.warn("NewsLine with ID {} not found", id);
        }
        return result;
    }

    @Transactional
    public NewsLineReadDto create(NewsLineCreateDto newsLineCreateDto) {
        return Optional.of(newsLineCreateDto)
                .map(newsLineMapper::toNewsLine)
                .map(entity -> {
                    NewsLineReadDto dto = newsLineMapper.toNewsLineReadDto(newsLineRepository.save(entity));
                    log.info("NewsLine created successfully with ID: {}", dto.id());
                    return dto;
                })
                .orElseThrow(() -> {
                    log.error("Creation failed: NewsLineCreateDto cannot be null");
                    return new EntityCreationException("NewsLineCreateDto cannot be null");
                });
    }

    @Transactional
    public NewsLineReadDto addLike(Long id) {
        return newsLineRepository.findById(id)
                .map(newsLine -> {
                     newsLine.setLikeCount(newsLine.getLikeCount() + 1);
                    log.info("Like added to NewsLine with ID: {}. New like count: {}", id, newsLine.getLikeCount());
                     return newsLineRepository.save(newsLine);
                })
                .map(newsLineMapper::toNewsLineReadDto)
                .orElseThrow(() -> {
                    log.error("NewsLine with ID {} not found when adding like", id);
                    return new EntityNotFoundException("NewsLine with id " + id + " not found");
                });
    }

    @Transactional
    public NewsLineReadDto addDislike(Long id) {
        return newsLineRepository.findById(id)
                .map(newsLine -> {
                     newsLine.setDislikeCount(newsLine.getDislikeCount() + 1);
                     log.info("Dislike added to NewsLine with ID: {}. New dislike count: {}", id, newsLine.getDislikeCount());
                     return newsLineRepository.save(newsLine);
                })
                .map(newsLineMapper::toNewsLineReadDto)
                .orElseThrow(() -> {
                    log.error("NewsLine with ID {} not found when adding dislike", id);
                    return new EntityNotFoundException("NewsLine with id " + id + " not found");
                });
    }

    @Transactional
    public boolean deleteById(Long id) {
        return newsLineRepository.findById(id)
                .map(newsLine -> {
                     newsLineRepository.delete(newsLine);
                    log.info("NewsLine with ID {} deleted successfully", id);
                     return true;
                 })
                .orElseGet(() -> {
                    log.warn("NewsLine with ID {} not found for deletion", id);
                    return false;
                });
    }

    public Page<NewsLineReadDto> findAllByOrderByLikeCountDesc(Pageable pageable) {
        Page<NewsLineReadDto> result = newsLineRepository.findAllByOrderByLikeCountDesc(pageable)
                .map(newsLineMapper::toNewsLineReadDto);
        log.info("Fetched {} NewsLines by like count descending", result.getTotalElements());
        return result;
    }

    public Page<NewsLineReadDto> findAllByOrderByPostDateDesc(Pageable pageable) {
        Page<NewsLineReadDto> result = newsLineRepository.findAllByOrderByPostDateDesc(pageable)
                .map(newsLineMapper::toNewsLineReadDto);
        log.info("Fetched {} NewsLines by post date descending", result.getTotalElements());
        return result;
    }
}


