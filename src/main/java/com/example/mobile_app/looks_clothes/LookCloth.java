package com.example.mobile_app.looks_clothes;

import com.example.mobile_app.clothes.model.Cloth;
import com.example.mobile_app.general.BaseEntity;
import com.example.mobile_app.looks.model.Look;

import jakarta.persistence.*;
import lombok.*;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "looks_clothes")
public class LookCloth implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "look_id")
    @ToString.Exclude
    private Look look;

    @ManyToOne
    @JoinColumn(name = "cloth_id")
    @ToString.Exclude
    private Cloth cloth;

    public static LookCloth create(Look look, Cloth cloth) {
        LookCloth lookCloth = new LookCloth();
        lookCloth.look = look;
        lookCloth.cloth = cloth;
        return lookCloth;
    }

}
