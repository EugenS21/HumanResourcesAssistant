package com.humanresources.assistant.restclient.service;

import com.humanresources.assistant.backend.dto.DepartmentDto;
import com.humanresources.assistant.restclient.CommonService;
import org.springframework.stereotype.Service;

@Service
public class DepartmentRestService extends CommonService<DepartmentDto> {

    public static final String DEPARTMENT = "department";

    @Override
    public String getURI() {
        return DEPARTMENT;
    }

    @Override
    public Class<DepartmentDto> getResponseClass() {
        return DepartmentDto.class;
    }
}
