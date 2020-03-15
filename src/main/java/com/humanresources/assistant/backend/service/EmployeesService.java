package com.humanresources.assistant.backend.service;

import com.humanresources.assistant.backend.model.Employee;
import com.humanresources.assistant.backend.repository.IUserRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeesService {

    @Autowired
    IUserRepository userRepository;

    public List<Employee> getAllEmployees() {
        List<Employee> usersList = new ArrayList<>();
        userRepository.findAll().forEach(usersList::add);
        return usersList;
    }

    public void saveUser(Employee employee) {
        userRepository.save(employee);
    }
}
