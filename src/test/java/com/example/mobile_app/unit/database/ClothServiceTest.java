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

    private final Long ClothId = 1L;
    private final Long lookId = 1L;
    private final Long UserId = 1L;
    private final String LinkPhoto = "https://www.example.com/photo.jpg";
    Cloth cloth;
    User user;
    UserReadDto userReadDto;
    ClothReadDto clothReadDto;
    ClothCreateDto clothCreateDto;



    @BeforeEach
    void setUp() {
        cloth = new Cloth();
        cloth.setId(ClothId);
        cloth.setUser(user);
        cloth.setLinkPhoto(LinkPhoto);
        cloth.setSeason(Season.WINTER);
        cloth.setType(Type.DRESS);

        clothReadDto = new ClothReadDto(ClothId, userReadDto, LinkPhoto, Season.WINTER, Type.DRESS);


        user = new User();
        user.setId(UserId);
        user.setName("John");
        user.setEmail("john@example.com");
        user.setPassword("password");
        user.setAvatar("https://www.example.com/avatar.jpg");
        user.setSize("M");

        userReadDto = new UserReadDto(UserId, "John", "john@example.com", "https://www.example.com/avatar.jpg", "M", "Doe", "johndoe");

        clothCreateDto = new ClothCreateDto(UserId, LinkPhoto, Season.WINTER, Type.DRESS);
    }

    @Nested
    @Tag("findById")
    class FindByIdTest {
        @Test
        void clothFound() {
            when(clothRepository.findById(ClothId)).thenReturn(Optional.of(cloth));
            when(clothMapper.toClothReadDto(cloth)).thenReturn(clothReadDto);

            var result = clothService.findById(ClothId);

            assertTrue(result.isPresent());
            assertEquals(clothReadDto, result.get());
            verify(clothRepository, times(1)).findById(ClothId);
            verify(clothMapper, times(1)).toClothReadDto(cloth);
        }

        @Test
        void clothNotFound() {
            when(clothRepository.findById(ClothId)).thenReturn(Optional.empty());

            var result = clothService.findById(ClothId);

            assertTrue(result.isEmpty());
            verify(clothRepository, times(1)).findById(ClothId);
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
            when(lookClothRepository.findAllClothByLookId(lookId)).thenReturn(Set.of(cloth));
            when(clothMapper.toClothReadDto(cloth)).thenReturn(clothReadDto);
            var result = clothService.findAllClothByLookId(lookId);

            assertEquals(1, result.size());
            assertEquals(clothReadDto, result.get(0));
            verify(lookClothRepository, times(1)).findAllClothByLookId(lookId);
            verify(clothMapper, times(1)).toClothReadDto(cloth);
        }

        @Test
        void clothesNotFound() {
            when(lookClothRepository.findAllClothByLookId(lookId)).thenReturn(Set.of());
            var result = clothService.findAllClothByLookId(lookId);

            assertEquals(0, result.size());
            verify(lookClothRepository, times(1)).findAllClothByLookId(lookId);
            verify(clothMapper, times(0)).toClothReadDto(cloth);
        }

        @Test
        void nullSetId() {
            when(lookClothRepository.findAllClothByLookId(null)).thenReturn(Set.of());

            var result = clothService.findAllClothByLookId(null);

            assertTrue(result.isEmpty());
            assertEquals(0, result.size());
            verify(lookClothRepository, times(1)).findAllClothByLookId(null);
            verify(clothMapper, times(0)).toClothReadDto(cloth);
        }
    }

//    @Nested
//    @Tag("create")
//    class CreateTest {
//        @Test
//        void clothCreated() {
//            when(clothMapper.toCloth(clothCreateDto)).thenReturn(cloth);
//            when(clothRepository.save(cloth)).thenReturn(cloth);
//            when(clothMapper.toClothReadDto(cloth)).thenReturn(clothReadDto);
//
//            var result = clothService.create(clothCreateDto);
//
//            assertEquals(clothReadDto, result);
//            verify(clothMapper, times(1)).toCloth(clothCreateDto);
//            verify(clothRepository, times(1)).save(cloth);
//            verify(clothMapper, times(1)).toClothReadDto(cloth);
//        }

//        @Test
//        void nullClothCreateDto() {
//
//            when(clothMapper.toCloth(null)).thenReturn(null);
//            when(clothRepository.save(any())).thenThrow(IllegalArgumentException.class);
//
//            assertThrows(IllegalArgumentException.class,
//                    () -> clothService.create(any()),
//                    "ClothCreateDto cannot be null");
//
//            verify(clothMapper, times(1)).toCloth(null);
//            verify(clothRepository, never()).save(any());
//            verify(clothMapper, never()).toClothReadDto(any());
//        }


//
//    }


    @Nested
    @Tag("findAllByUserIdAndType")
    class FindAllByUserIdAndTypeTest {
        @Test
        void clothesFound() {
        }
        @Test
        void clothesNotFound() {
        }
        @Test
        void nullUserId() {
        }
        @Test
        void nullType() {

        }
    }

    @Nested
    @Tag("deleteById")
    class DeleteByIdTest {
        @Test
        void clothDeleted() {
        }
        @Test
        void clothNotFound() {
        }
        @Test
        void nullId() {
        }
    }


}
