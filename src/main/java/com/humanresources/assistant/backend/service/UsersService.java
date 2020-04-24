package com.humanresources.assistant.backend.service;

import static com.google.common.collect.Lists.newArrayList;

import com.humanresources.assistant.backend.converters.UserConverters;
import com.humanresources.assistant.backend.dto.UserDto;
import com.humanresources.assistant.backend.repository.authentication.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    @Autowired
    UserConverters userConverters;

    @Autowired
    UserRepository usersRepository;

    public List<UserDto> getAllUsers() {
        return userConverters.convertUserEntityListToDto.apply(newArrayList(usersRepository.findAll()));
    }

    public void deleteUser(Long userId) {
        usersRepository.deleteById(userId);
    }
}
