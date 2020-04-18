package com.humanresources.assistant.backend.controller;

import com.humanresources.assistant.backend.model.Employee;
import com.humanresources.assistant.backend.service.EmployeesService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeesRestController {

    @Autowired
    EmployeesService employeesService;

    @GetMapping ("/users")
    public List<Employee> getAllProducts() {
        return employeesService.getAllEmployees();
    }

    @RequestMapping (method = RequestMethod.POST, value = "/users")
    public void postNewUser(@RequestBody Employee employee) {
        employeesService.saveUser(employee);
    }
}
