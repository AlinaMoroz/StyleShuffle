package com.example.mobile_app.looks.model;

import com.example.mobile_app.clothes.model.Cloth;
import com.example.mobile_app.general.BaseEntity;
import com.example.mobile_app.looks_clothes.LookCloth;
import com.example.mobile_app.users.modal.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


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

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private User user;

    @Builder.Default
    @OneToMany(mappedBy = "look", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<LookCloth> lookClothes = new HashSet<>();

    public void addCloth(Cloth cloth) {
        LookCloth lookCloth = LookCloth.create(this, cloth);
        lookClothes.add(lookCloth);
    }

    public void removeCloth(Cloth cloth) {
        Optional<LookCloth> relation = lookClothes.stream()
                .filter(lc -> lc.getCloth().equals(cloth))
                .findFirst();
        relation.ifPresent(lc -> {
            lookClothes.remove(lc);
        });
    }
}
