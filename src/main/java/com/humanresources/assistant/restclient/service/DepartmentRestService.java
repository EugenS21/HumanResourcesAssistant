package com.humanresources.assistant.restclient.service;

import com.humanresources.assistant.restclient.CommonService;
import com.humanresources.assistant.restclient.model.Department;
import org.springframework.stereotype.Service;

@Service
public class DepartmentRestService extends CommonService<Department> {

    public static final String DEPARTMENT = "department";

    @Override
    public String getURI() {
        return DEPARTMENT;
    }

    @Override
    public Class<Department> getResponseClass() {
        return Department.class;
    }
}
