package com.example.mobile_app.looks_clothes;

import com.example.mobile_app.clothes.model.Cloth;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.Set;


public interface LookClothRepository extends JpaRepository<LookCloth, Long> {

    Set<Cloth> findAllClothByLookId(Long lookId);

    Optional<LookCloth> findByLookIdAndClothId(Long lookId, Long clothId);
}
