package com.example.mobile_app.users.modal;

import com.example.mobile_app.clothes.model.Cloth;
import com.example.mobile_app.general.BaseEntity;
import com.example.mobile_app.looks.model.Look;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String avatar;

    private String size;

    private String surname;

    private String username;


    @Builder.Default
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Cloth> cloths = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Look> looks = new ArrayList<>();


    public void addCloth(Cloth cloth){
        cloths.add(cloth);
        cloth.setUser(this);
    }

    public void addSet(Look look){
        looks.add(look);
        look.setUser(this);
    }


}
