package com.example.mobile_app.unit.database;

import com.example.mobile_app.clothes.service.ClothService;
import com.example.mobile_app.clothes.Season;
import com.example.mobile_app.clothes.Type;
import com.example.mobile_app.clothes.dto.ClothCreateDto;
import com.example.mobile_app.clothes.dto.ClothReadDto;
import com.example.mobile_app.clothes.mapper.ClothMapper;
import com.example.mobile_app.clothes.model.Cloth;
import com.example.mobile_app.clothes.repository.ClothRepository;
import com.example.mobile_app.looks_clothes.LookClothRepository;
import com.example.mobile_app.users.dto.UserReadDto;
import com.example.mobile_app.users.modal.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClothServiceTest {

    @Mock
    private ClothRepository clothRepository;
    @Mock
    private ClothMapper clothMapper;
    @Mock
    private LookClothRepository lookClothRepository;
    @InjectMocks
    private ClothService clothService;

    private static final Long CLOTH_ID = 1L;
    private static final Long LOOK_ID = 1L;
    private static final Long USER_ID = 1L;
    private static final String LINK_PHOTO = "https://www.example.com/photo.jpg";
    Cloth cloth;
    User user;
    UserReadDto userReadDto;
    ClothReadDto clothReadDto;
    ClothCreateDto clothCreateDto;



    @BeforeEach
    void setUp() {
        cloth = new Cloth();
        cloth.setId(CLOTH_ID);
        cloth.setUser(user);
        cloth.setLinkPhoto(LINK_PHOTO);
        cloth.setSeason(Season.WINTER);
        cloth.setType(Type.DRESS);

        clothReadDto = new ClothReadDto(CLOTH_ID, LINK_PHOTO, Season.WINTER, Type.DRESS);

        user = new User();
        user.setId(USER_ID);
        user.setName("John");
        user.setEmail("john@example.com");
        user.setPassword("password");
        user.setAvatar("https://www.example.com/avatar.jpg");
        user.setSize("M");

        userReadDto = new UserReadDto(USER_ID, "John", "john@example.com", "https://www.example.com/avatar.jpg", "M", "Doe", "johndoe");

        clothCreateDto = new ClothCreateDto(USER_ID, LINK_PHOTO, Season.WINTER, Type.DRESS);
    }

    @Nested
    @Tag("findById")
    class FindByIdTest {
        @Test
        void clothFound() {
            when(clothRepository.findById(CLOTH_ID)).thenReturn(Optional.of(cloth));
            when(clothMapper.toClothReadDto(cloth)).thenReturn(clothReadDto);

            var result = clothService.findById(CLOTH_ID);

            assertTrue(result.isPresent());
            assertEquals(clothReadDto, result.get());
            verify(clothRepository, times(1)).findById(CLOTH_ID);
            verify(clothMapper, times(1)).toClothReadDto(cloth);
        }

        @Test
        void clothNotFound() {
            when(clothRepository.findById(CLOTH_ID)).thenReturn(Optional.empty());

            var result = clothService.findById(CLOTH_ID);

            assertTrue(result.isEmpty());
            verify(clothRepository, times(1)).findById(CLOTH_ID);
            verify(clothMapper, times(0)).toClothReadDto(cloth);
        }

        @Test
        void nullId() {
            var result = clothService.findById(null);

            assertTrue(result.isEmpty());

            verify(clothRepository, times(1)).findById(null);
            verify(clothMapper, times(0)).toClothReadDto(cloth);
        }

    }

    @Nested
    @Tag("findByLookId")
    class FindAllClothByLookIdTest {
        @Test
        void clothesFound() {
            when(lookClothRepository.findAllClothByLookId(LOOK_ID)).thenReturn(Set.of(cloth));
            when(clothMapper.toClothReadDto(cloth)).thenReturn(clothReadDto);
            var result = clothService.findAllClothByLookId(LOOK_ID);

            assertEquals(1, result.size());
            assertEquals(clothReadDto, result.get(0));
            verify(lookClothRepository, times(1)).findAllClothByLookId(LOOK_ID);
            verify(clothMapper, times(1)).toClothReadDto(cloth);
        }

        @Test
        void clothesNotFound() {
            when(lookClothRepository.findAllClothByLookId(LOOK_ID)).thenReturn(Set.of());
            var result = clothService.findAllClothByLookId(LOOK_ID);

            assertEquals(0, result.size());
            verify(lookClothRepository, times(1)).findAllClothByLookId(LOOK_ID);
            verify(clothMapper, times(0)).toClothReadDto(cloth);
        }

        @Test
        void nullSetId() {
            when(lookClothRepository.findAllClothByLookId(null)).thenReturn(Set.of());

            var result = clothService.findAllClothByLookId(null);

            assertTrue(result.isEmpty());
            verify(lookClothRepository, times(1)).findAllClothByLookId(null);
            verify(clothMapper, times(0)).toClothReadDto(cloth);
        }
    }

}
