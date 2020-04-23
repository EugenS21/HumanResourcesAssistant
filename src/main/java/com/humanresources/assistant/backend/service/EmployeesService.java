package com.humanresources.assistant.backend.service;

import static com.google.common.collect.Lists.newArrayList;

import com.humanresources.assistant.backend.converters.EmployeeConverters;
import com.humanresources.assistant.backend.dto.EmployeeDto;
import com.humanresources.assistant.backend.entity.Employee;
import com.humanresources.assistant.backend.exceptions.EmployeeNotFound;
import com.humanresources.assistant.backend.repository.EmployeeRepository;
import java.util.List;
import java.util.Optional;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeesService {

    @Autowired
    EmployeeConverters employeeConverters;

    @Autowired
    EmployeeRepository employeeRepository;

    public List<EmployeeDto> getAllEmployees() {
        return employeeConverters.convertEmployeeEntityListToDto.apply(newArrayList(employeeRepository.findAll()));
    }

    public void saveUser(EmployeeDto employee) {
        employeeRepository.save(employeeConverters.convertEmployeeDtoToEntity.apply(employee));
    }

    @SneakyThrows
    public EmployeeDto findById(String employeeId) {
        final Optional<Employee> foundEmployee = employeeRepository.findById(employeeId);
        return employeeConverters.convertEmployeeEntityToDto.apply(foundEmployee.orElseThrow(EmployeeNotFound::new));
    }

    public void deleteEmployee(String employeeId) {
        employeeRepository.deleteById(employeeId);
    }

    public void deleteEmployee(EmployeeDto employeeDto) {
        employeeRepository.delete(employeeConverters.convertEmployeeDtoToEntity.apply(employeeDto));
    }
}
