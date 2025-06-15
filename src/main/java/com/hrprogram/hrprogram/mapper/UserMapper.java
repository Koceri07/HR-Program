package com.hrprogram.hrprogram.mapper;

import com.hrprogram.hrprogram.entity.UserEntity;
import com.hrprogram.hrprogram.model.dto.UserDto;
import com.hrprogram.hrprogram.model.request.UserRequest;
import com.hrprogram.hrprogram.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toDto(UserEntity userEntity);

    UserEntity responseToEntity(UserResponse userResponse);

    UserEntity requestToEntity(UserRequest userRequest);

    UserResponse entityToResponse(UserEntity userEntity);

    UserRequest responseToRequest(UserResponse userResponse);

}
