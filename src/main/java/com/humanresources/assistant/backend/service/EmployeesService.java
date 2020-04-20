package com.humanresources.assistant.backend.service;

import com.humanresources.assistant.backend.entity.Employee;
import com.humanresources.assistant.backend.repository.IEmployeeRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeesService {

    @Autowired
    IEmployeeRepository userRepository;

    public List<Employee> getAllEmployees() {
        List<Employee> employeesList = new ArrayList<>();
        userRepository.findAll().forEach(employeesList::add);
        return employeesList;
    }

    public void saveUser(Employee employee) {
        userRepository.save(employee);
    }
}
