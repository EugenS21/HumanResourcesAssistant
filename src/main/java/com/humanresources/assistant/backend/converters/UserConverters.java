package com.humanresources.assistant.backend.converters;

import com.humanresources.assistant.backend.dto.UserDto;
import com.humanresources.assistant.backend.entity.authentication.UserEntity;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class UserConverters extends Generic<UserEntity, UserDto> {

    public Function<List<UserEntity>, List<UserDto>> convertUserEntityListToDto = convertListEntitiesToDto();
    public Function<UserEntity, UserDto> convertUserEntityToDto = convertEntityToDto();
    public Function<UserDto, UserEntity> convertUserDtoToEntity = convertDtoToEntity();


    @Override
    protected Function<UserEntity, UserDto> convertEntityToDto() {
        return user -> UserDto.builder()
            .id(user.getId())
            .username(user.getUsername())
            .email(user.getEmail())
            .build();
    }

    @Override
    protected Function<UserDto, UserEntity> convertDtoToEntity() {
        return user -> UserEntity.builder()
            .id(user.getId())
            .username(user.getUsername())
            .email(user.getEmail())
            .build();
    }

    @Override
    protected Function<List<UserEntity>, List<UserDto>> convertListEntitiesToDto() {
        return users -> users.stream()
            .map(user -> convertEntityToDto().apply(user))
            .collect(Collectors.toList());
    }

    @Override
    protected Function<List<UserDto>, List<UserEntity>> convertListDtoToEntities() {
        return users -> users.stream()
            .map(user -> convertDtoToEntity().apply(user))
            .collect(Collectors.toList());
    }

}
