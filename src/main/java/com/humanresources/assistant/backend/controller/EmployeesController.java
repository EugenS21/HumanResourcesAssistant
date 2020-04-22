package com.humanresources.assistant.backend.controller;

import com.humanresources.assistant.backend.entity.Employee;
import com.humanresources.assistant.backend.service.EmployeesService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeesController {

    @Autowired
    EmployeesService employeesService;

    @GetMapping ("/employee")
    @PreAuthorize ("hasRole('hr') or hasRole('admin')")
    public List<Employee> getAllEmployees() {
        return employeesService.getAllEmployees();
    }

    @RequestMapping (method = RequestMethod.POST, value = "/employee")
    @PreAuthorize ("hasRole('hr') or hasRole('admin')")
    public void postNewEmployee(@RequestBody Employee employee) {
        employeesService.saveUser(employee);
    }
}
