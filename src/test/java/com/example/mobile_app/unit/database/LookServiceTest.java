package com.example.mobile_app.unit.database;

import com.example.mobile_app.clothes.Season;
import com.example.mobile_app.clothes.Type;
import com.example.mobile_app.clothes.dto.ClothReadDto;
import com.example.mobile_app.clothes.model.Cloth;
import com.example.mobile_app.clothes.repository.ClothRepository;
import com.example.mobile_app.exception.EntityCreationException;
import com.example.mobile_app.looks.LookRepository;
import com.example.mobile_app.looks.dto.LookCreateDto;
import com.example.mobile_app.looks.dto.LookReadDto;
import com.example.mobile_app.looks.dto.LookUpdateDto;
import com.example.mobile_app.looks.mapper.LookMapper;
import com.example.mobile_app.looks.model.Look;
import com.example.mobile_app.looks.service.LookService;
import com.example.mobile_app.users.dto.UserReadDto;
import com.example.mobile_app.users.modal.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class LookServiceTest {

    @Mock
    private LookRepository lookRepository;
    @Mock
    private LookMapper lookMapper;
    @Mock
    private ClothRepository clothRepository;
    @InjectMocks
    private LookService lookService;

    private static final Long LOOK_ID = 1L;
    private static final Long USER_ID = 1L;
    private static final Long CLOTH_ID = 1L;

    private Look look;
    private Cloth cloth;
    private LookUpdateDto lookUpdateDto;
    private LookCreateDto lookCreateDto;
    private LookReadDto lookReadDto;


    @BeforeEach
    void setUp() {

        User user = new User();
        user.setId(USER_ID);
        user.setName("John");
        user.setEmail("john@example.com");
        user.setPassword("password");
        user.setAvatar("https://www.example.com/avatar.jpg");
        user.setSize("M");
        user.setSurname("Dao");

        cloth = new Cloth();
        cloth.setId(CLOTH_ID);
        cloth.setUser(user);
        cloth.setLinkPhoto("https://www.example.com/photo.jpg");
        cloth.setSeason(Season.SUMMER);
        cloth.setType(Type.DRESS);
        cloth.setBrand("brand");
        cloth.setColor("color");

        look = new Look();
        look.setId(LOOK_ID);
        look.setDescription("description");
        look.setName("name");
        look.setUser(user);

        Cloth cloth2 = new Cloth();
        cloth2.setId(2L);
        cloth2.setUser(user);
        cloth2.setLinkPhoto("https://www.example.com/photo2.jpg");
        cloth2.setSeason(Season.SUMMER);
        cloth2.setType(Type.DRESS);
        cloth2.setBrand("brand");
        cloth2.setColor("color");

        ClothReadDto clothReadDto = new ClothReadDto(CLOTH_ID, "https://www.example.com/photo.jpg", Season.SUMMER, Type.DRESS);
        UserReadDto userReadDto = new UserReadDto(USER_ID, "John", "john@example.com", "https://www.example.com/avatar.jpg", "M", "Doe", "johndoe");

        lookReadDto = new LookReadDto(LOOK_ID, "description", "name", userReadDto,Set.of(clothReadDto) );
        lookUpdateDto = new LookUpdateDto( "new description", "new name");
        lookCreateDto = new LookCreateDto("description", "name", USER_ID, Set.of(CLOTH_ID));
    }


    @Nested
    @Tag("findById")
    class FindById {
        @Test
        void lookFound() {
            when(lookRepository.findById(LOOK_ID)).thenReturn(Optional.of(look));
            when(lookMapper.toLookReadDto(look)).thenReturn(lookReadDto);

            var result = lookService.findById(LOOK_ID);

            assertEquals(Optional.of(lookReadDto), result);
            verify(lookRepository, times(1)).findById(LOOK_ID);
            verify(lookMapper, times(1)).toLookReadDto(look);
        }

        @Test
        void lookNotFound() {
            when(lookRepository.findById(LOOK_ID)).thenReturn(Optional.empty());

            var result = lookService.findById(LOOK_ID);

            assertFalse(result.isPresent());
            verify(lookRepository, times(1)).findById(LOOK_ID);
            verify(lookMapper, times(0)).toLookReadDto(look);
        }

        @Test
        void nullId() {
            when(lookRepository.findById(null)).thenReturn(Optional.empty());

            var result = lookService.findById(null);

            assertFalse(result.isPresent());
            verify(lookRepository, times(1)).findById(null);
            verify(lookMapper, never()).toLookReadDto(any());
        }
    }

    @Nested
    @Tag("findAllByUserId")
    class FindAllByUserId {
        private final Pageable pageable = PageRequest.of(0, 10);

        @Test
        void looksFound() {
            Page<Look> looksPage = new PageImpl<>(Collections.singletonList(look));

            when(lookRepository.findAllByUserId(USER_ID, pageable)).thenReturn(looksPage);
            when(lookMapper.toLookReadDto(look)).thenReturn(lookReadDto);

            var result = lookService.findAllByUserId(USER_ID, pageable);

            assertEquals(1, result.getTotalElements());
            assertEquals(1, result.getContent().size());
            assertEquals(lookReadDto, result.getContent().get(0)); // Проверка содержимого
            verify(lookRepository, times(1)).findAllByUserId(USER_ID, pageable);
            verify(lookMapper, times(1)).toLookReadDto(look);
        }

        @Test
        void looksNotFound() {
            Page<Look> looksPage = new PageImpl<>(Collections.emptyList());

            when(lookRepository.findAllByUserId(USER_ID, pageable)).thenReturn(looksPage);

            var result = lookService.findAllByUserId(USER_ID, pageable);

            assertEquals(0, result.getTotalElements());
            verify(lookRepository, times(1)).findAllByUserId(USER_ID, pageable);
            verify(lookMapper, times(0)).toLookReadDto(any());
        }

        @Test
        void nullUserId() {
            when(lookRepository.findAllByUserId(null, pageable)).thenReturn(Page.empty());

            var result = lookService.findAllByUserId(null, pageable);

            assertEquals(0, result.getTotalElements());
            verify(lookRepository, times(1)).findAllByUserId(null, pageable);
            verify(lookMapper, never()).toLookReadDto(any());
        }
    }

    @Nested
    @Tag("deleteById")
    class DeleteById {
        @Test
        void lookDeleted() {
            when(lookRepository.findById(LOOK_ID)).thenReturn(Optional.of(look));

            var result = lookService.deleteById(LOOK_ID);

            assertTrue(result);
            verify(lookRepository, times(1)).findById(LOOK_ID);
            verify(lookRepository, times(1)).delete(look);
        }

        @Test
        void lookNotFound() {
            when(lookRepository.findById(LOOK_ID)).thenReturn(Optional.empty());

            var result = lookService.deleteById(LOOK_ID);

            assertFalse(result);
            verify(lookRepository, times(1)).findById(LOOK_ID);
            verify(lookRepository, never()).delete(look);
        }

        @Test
        void nullId() {
            when(lookRepository.findById(null)).thenReturn(Optional.empty());

            var result = lookService.deleteById(null);

            assertFalse(result);
            verify(lookRepository, times(1)).findById(null);
            verify(lookRepository, never()).delete(any());
        }
    }

    @Nested
    @Tag("update")
    class Update {
        @Test
        void lookUpdated() {
            when(lookRepository.findById(LOOK_ID)).thenReturn(Optional.of(look));
            doAnswer(invocation -> {
                look.setName(lookUpdateDto.getName());
                look.setDescription(lookUpdateDto.getDescription());
                return null;
            }).when(lookMapper).updateLook(eq(lookUpdateDto), eq(look));
            when(lookMapper.toLookReadDto(look)).thenReturn(lookReadDto);

            var result = lookService.update(LOOK_ID, lookUpdateDto);

            assertTrue(result.isPresent());
            assertEquals(lookReadDto, result.get());
            verify(lookRepository).findById(LOOK_ID);
            verify(lookMapper).updateLook(lookUpdateDto, look);
            verify(lookMapper).toLookReadDto(look);
        }

        @Test
        void lookNotFound() {
            when(lookRepository.findById(LOOK_ID)).thenReturn(Optional.empty());

            var result = lookService.update(LOOK_ID,lookUpdateDto);

            assertFalse(result.isPresent());
            verify(lookRepository, times(1)).findById(LOOK_ID);
            verify(lookRepository, never()).save(look);
        }

        @Test
        void nullId() {
            when(lookRepository.findById(null)).thenReturn(Optional.empty());
            var result = lookService.update(null, lookUpdateDto);

            assertFalse(result.isPresent());
            verify(lookRepository, times(1)).findById(null);
            verify(lookRepository, never()).save(any());
        }
    }

    @Nested
    @Tag("create")
    class Create {
        @Test
        void lookCreated() {
            when(lookMapper.toLook(lookCreateDto)).thenReturn(look);
            when(lookRepository.save(look)).thenReturn(look);
            when(lookMapper.toLookReadDto(look)).thenReturn(lookReadDto);
            when(clothRepository.findById(CLOTH_ID)).thenReturn(Optional.of(cloth));

            var result = lookService.create(lookCreateDto);

            assertEquals(lookReadDto, result);
            verify(lookMapper, times(1)).toLook(lookCreateDto);
            verify(lookRepository, times(1)).save(look);
            verify(lookMapper, times(1)).toLookReadDto(look);
        }

        @Test
        void nullLook() {
            assertThrows(EntityCreationException.class, () -> {
                lookService.create(null);
            });

            verify(lookRepository, never()).save(any());
            verify(lookMapper, never()).toLook(any());
        }

    }

    @Nested
    @Tag("addClothToLook")
    class AddClothToLook {
        @Test
        void clothAdded() {
            when(lookRepository.findById(LOOK_ID)).thenReturn(Optional.of(look));
            when(clothRepository.findById(CLOTH_ID)).thenReturn(Optional.of(cloth));

            lookService.addClothToLook(LOOK_ID, CLOTH_ID);

            assertEquals(1, look.getLookClothes().size());
            verify(lookRepository, times(1)).save(look);
        }

        @Test
        void lookNotFound() {
            when(lookRepository.findById(LOOK_ID)).thenThrow(IllegalArgumentException.class);

            assertThrows(IllegalArgumentException.class, () -> {
                lookService.addClothToLook(LOOK_ID, CLOTH_ID);
            });

            verify(lookRepository, times(1)).findById(LOOK_ID);
            verify(lookRepository, never()).save(any());
        }


        @Test
        void nullLookId() {
            assertThrows(IllegalArgumentException.class, () -> {
                lookService.addClothToLook(null, CLOTH_ID);
            });

            verify(lookRepository, never()).findById(any());
            verify(lookRepository, never()).save(any());
        }

        @Test
        void nullClothId() {
            assertThrows(IllegalArgumentException.class, () -> {
                lookService.addClothToLook(LOOK_ID, null);
            });

            verify(clothRepository, never()).findById(any());
            verify(lookRepository, never()).save(any());
        }


    }

    @Nested
    @Tag("removeClothingFromLook")
    class RemoveClothingFromLook {
        @Test
        void clothingRemoved() {
            when(lookRepository.findById(LOOK_ID)).thenReturn(Optional.of(look));
            lenient().when(clothRepository.findById(CLOTH_ID)).thenReturn(Optional.of(cloth));
            lookService.removeClothFromLook(LOOK_ID, CLOTH_ID);

            assertEquals(0, look.getLookClothes().size());

            verify(lookRepository, times(1)).save(look);
        }

        @Test
        void lookNotFound() {
            when(lookRepository.findById(LOOK_ID)).thenThrow(NoSuchElementException.class);

            assertThrows(NoSuchElementException.class, () -> {
                lookService.removeClothFromLook(LOOK_ID, CLOTH_ID);
            });
        }

        @Test
        void nullLookId() {
            assertThrows(IllegalArgumentException.class, () -> {
                lookService.removeClothFromLook(null, CLOTH_ID);
            });

            verify(lookRepository, never()).findById(any());
            verify(lookRepository, never()).save(any());
        }

        @Test
        void nullClothId() {
            assertThrows(IllegalArgumentException.class, () -> {
                lookService.removeClothFromLook(LOOK_ID, null);
            });

            verify(clothRepository, never()).findById(any());
            verify(lookRepository, never()).save(any());
        }
    }
}
