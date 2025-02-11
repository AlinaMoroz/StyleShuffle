package com.example.mobile_app.users.mapper;

import com.example.mobile_app.users.dto.NewUserRequestDto;
import com.example.mobile_app.users.dto.NewUserResponseDto;
import com.example.mobile_app.users.dto.UserReadDto;
import com.example.mobile_app.users.dto.UserUpdateDto;
import com.example.mobile_app.users.modal.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "avatar", ignore = true)
    @Mapping(target = "size", ignore = true)
    @Mapping(target = "surname", ignore = true)
    @Mapping(target = "cloths", ignore = true)
    @Mapping(target = "looks", ignore = true)
    User toUser(NewUserRequestDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "cloths", ignore = true)
    @Mapping(target = "looks", ignore = true)
    void updateUser(UserUpdateDto dto, @MappingTarget User user);

    NewUserResponseDto toNewUserResponseDto(User user);

    UserReadDto toUserReadDto(User user);
}
