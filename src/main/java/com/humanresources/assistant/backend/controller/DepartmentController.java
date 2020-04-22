package com.humanresources.assistant.backend.controller;

import com.humanresources.assistant.backend.entity.Department;
import com.humanresources.assistant.backend.service.DepartmentService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @RequestMapping (method = RequestMethod.GET, value = "/department")
    @PreAuthorize ("hasAnyAuthority('hr','admin')")
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @RequestMapping (method = RequestMethod.POST, value = "/department")
    @PreAuthorize ("hasAuthority('admin')")
    public void postNewDepartment(@Valid @RequestBody Department department) {
        departmentService.saveDepartment(department);
    }
}
