package com.example.mobile_app.looks_clothes;

import com.example.mobile_app.clothes.model.Cloth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface LookClothRepository extends JpaRepository<LookCloth, Long> {


//@Query("SELECT sc.cloth FROM SetCloth sc WHERE sc.set.id = :setId")

    List<Cloth> findAllClothByLookId(Long lookId);
}
