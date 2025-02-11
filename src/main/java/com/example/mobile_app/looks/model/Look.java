package com.example.mobile_app.looks.model;

import com.example.mobile_app.general.BaseEntity;
import com.example.mobile_app.newslines.model.NewsLine;
import com.example.mobile_app.looks_clothes.LookCloth;
import com.example.mobile_app.users.modal.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;


@Entity
@Table(name = "looks")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Look implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private NewsLine newsLine;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private User user;

    @Builder.Default
    @OneToMany(mappedBy = "look")
    @ToString.Exclude
    private java.util.Set<LookCloth> lookClothes = new HashSet<>();

    public void addSetCloth(LookCloth lookCloth) {
        lookClothes.add(lookCloth);
        lookCloth.setLook(this);
    }

    public void removeSetCloth(LookCloth lookCloth) {
        lookClothes.remove(lookCloth);
        lookCloth.setLook(null);
    }


}
