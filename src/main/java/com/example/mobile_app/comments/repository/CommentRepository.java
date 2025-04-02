package com.example.mobile_app.comments.repository;

import com.example.mobile_app.comments.modal.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;



public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByNewsLineId(Long newsLineId);

}
