package com.example.mobile_app.comments.repository;

import com.example.mobile_app.comments.modal.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;



public interface CommentRepository extends JpaRepository<Comment, Long> {


//    @Query("select c from Comment c " +
//    "join fetch c.user u where c.newsLine.id = :newsLine")

//    @Query("select c from Comment c " +
//    "join fetch c.user u where c.newsLine = :newsLine")
//    List<Comment> findAllByNewsLine(@Param("newsLine") NewsLine newsLine);

    List<Comment> findAllByNewsLineId(Long newsLineId);


}
