package com.humanresources.assistant.restclient.service;

import com.humanresources.assistant.backend.dto.EmployeeDto;
import com.humanresources.assistant.restclient.CommonService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeRestService extends CommonService<EmployeeDto> {

    public static final String EMPLOYEE = "employee";

    @Override
    public String getURI() {
        return EMPLOYEE;
    }

    @Override
    public Class<EmployeeDto> getResponseClass() {
        return EmployeeDto.class;
    }
}
