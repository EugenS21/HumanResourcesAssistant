package com.humanresources.assistant.backend.converters;

import com.humanresources.assistant.backend.dto.UserDto;
import com.humanresources.assistant.backend.entity.authentication.User;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class UserConverters extends Generic<User, UserDto> {

    public Function<List<User>, List<UserDto>> convertUserEntityListToDto = convertListEntitiesToDto();
    public Function<User, UserDto> convertUserEntityToDto = convertEntityToDto();
    public Function<UserDto, User> convertUserDtoToEntity = convertDtoToEntity();


    @Override
    protected Function<User, UserDto> convertEntityToDto() {
        return user -> UserDto.builder()
            .id(user.getId())
            .username(user.getUsername())
            .email(user.getEmail())
            .build();
    }

    @Override
    protected Function<UserDto, User> convertDtoToEntity() {
        return user -> User.builder()
            .id(user.getId())
            .username(user.getUsername())
            .email(user.getEmail())
            .build();
    }

    @Override
    protected Function<List<User>, List<UserDto>> convertListEntitiesToDto() {
        return users -> users.stream()
            .map(user -> convertEntityToDto().apply(user))
            .collect(Collectors.toList());
    }

    @Override
    protected Function<List<UserDto>, List<User>> convertListDtoToEntities() {
        return users -> users.stream()
            .map(user -> convertDtoToEntity().apply(user))
            .collect(Collectors.toList());
    }

}
