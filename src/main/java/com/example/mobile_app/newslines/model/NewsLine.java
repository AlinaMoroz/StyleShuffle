package com.example.mobile_app.newslines.model;

import com.example.mobile_app.comments.modal.Comment;
import com.example.mobile_app.general.BaseEntity;
import com.example.mobile_app.looks.model.Look;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "news_lines")
public class NewsLine implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(name = "look_id")
    private Look look;

    @Column(name = "post_date")
    private LocalDateTime postDate;

    @Builder.Default
    @Column(name = "like_count")
    private Integer likeCount = 0;

    @Builder.Default
    @Column(name = "dislike_count")
    private Integer dislikeCount = 0;

    @Builder.Default
    @ToString.Exclude
    @OneToMany(mappedBy = "newsLine")
    List<Comment> comments = new ArrayList<>();

    public void addComment(Comment comment){
        comments.add(comment);
        comment.setNewsLine(this);
    }



}
