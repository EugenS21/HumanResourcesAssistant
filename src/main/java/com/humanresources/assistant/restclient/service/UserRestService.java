package com.humanresources.assistant.restclient.service;

import com.humanresources.assistant.backend.dto.UserDto;
import com.humanresources.assistant.restclient.CommonService;
import org.springframework.stereotype.Service;

@Service
public class UserRestService extends CommonService<UserDto> {

    public static final String USER = "user";

    @Override
    public String getURI() {
        return USER;
    }

    @Override
    public Class<UserDto> getResponseClass() {
        return UserDto.class;
    }

}
