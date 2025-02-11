package com.example.mobile_app.clothes.repository;

import com.example.mobile_app.clothes.Type;
import com.example.mobile_app.clothes.model.Cloth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ClothRepository extends JpaRepository<Cloth,Long > {


//    List<Cloth> findAllByUserAndType(User user, Type type);

//    List<Cloth> findAllBySetCloth

    List<Cloth> findAllByUserIdAndType(Long userId, Type type);

}
