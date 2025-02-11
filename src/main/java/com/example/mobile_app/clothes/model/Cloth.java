package com.example.mobile_app.clothes.model;

import com.example.mobile_app.clothes.Type;
import com.example.mobile_app.clothes.Season;
import com.example.mobile_app.general.BaseEntity;
import com.example.mobile_app.looks_clothes.LookCloth;
import com.example.mobile_app.users.modal.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "clothes")
public class Cloth  implements BaseEntity<Long> {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private User user;


    @Column(name = "link_photo")
    private String linkPhoto;

    @Enumerated(EnumType.STRING)
    private Season season;


    @Enumerated(EnumType.STRING)
    private Type type;

    private String brand;

    private String color;

    @Builder.Default
    @OneToMany(mappedBy = "cloth")
    @ToString.Exclude
    private List<LookCloth> lookClothes = new ArrayList<>();



}
