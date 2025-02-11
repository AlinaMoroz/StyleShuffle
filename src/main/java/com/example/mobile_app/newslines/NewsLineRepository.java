package com.example.mobile_app.newslines;

import com.example.mobile_app.newslines.model.NewsLine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NewsLineRepository extends JpaRepository<NewsLine, Long> {

    Page<NewsLine> findAllByOrderByPostDateDesc(Pageable pageable);
    Page<NewsLine> findAllByOrderByLikeCountDesc(Pageable  pageable);



}
