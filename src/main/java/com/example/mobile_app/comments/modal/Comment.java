package com.example.mobile_app.comments.modal;

import com.example.mobile_app.newslines.model.NewsLine;
import com.example.mobile_app.general.BaseEntity;
import com.example.mobile_app.users.modal.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "comments")
public class Comment implements BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private User user;


    private String text;

    @Column(name = "date_post")
    private LocalDateTime datePost;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private NewsLine newsLine;

}
