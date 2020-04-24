package com.humanresources.assistant.restclient.service;

import com.humanresources.assistant.backend.dto.ClientDto;
import com.humanresources.assistant.restclient.CommonService;
import org.springframework.stereotype.Service;

@Service
public class ClientRestService extends CommonService<ClientDto> {

    public static final String CLIENT = "client";

    @Override
    public String getURI() {
        return CLIENT;
    }

    @Override
    public Class<ClientDto> getResponseClass() {
        return ClientDto.class;
    }
}
