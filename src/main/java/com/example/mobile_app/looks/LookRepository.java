package com.example.mobile_app.looks;

import com.example.mobile_app.looks.model.Look;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LookRepository extends JpaRepository<Look, Long> {
    Page<Look> findAllByUserId(Long userId, Pageable pageable);

}
