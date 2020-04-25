package com.humanresources.assistant.restclient.service;

import com.humanresources.assistant.backend.dto.GradeDto;
import com.humanresources.assistant.restclient.CommonService;
import org.springframework.stereotype.Service;

@Service
public class GradeRestService extends CommonService<GradeDto> {

    public static final String DEPARTMENT = "grade";

    @Override
    public String getURI() {
        return DEPARTMENT;
    }

    @Override
    public Class<GradeDto> getResponseClass() {
        return GradeDto.class;
    }
}
