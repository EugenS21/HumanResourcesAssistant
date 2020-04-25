package com.humanresources.assistant.restclient.service;

import com.humanresources.assistant.backend.dto.LocationDto;
import com.humanresources.assistant.restclient.CommonService;
import org.springframework.stereotype.Service;

@Service
public class LocationRestService extends CommonService<LocationDto> {

    public static final String LOCATION = "location";

    @Override
    public String getURI() {
        return LOCATION;
    }

    @Override
    public Class<LocationDto> getResponseClass() {
        return LocationDto.class;
    }
}
