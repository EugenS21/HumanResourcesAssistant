package com.humanresources.assistant.backend.converters;

import com.humanresources.assistant.backend.dto.EmployeeDto;
import com.humanresources.assistant.backend.entity.Employee;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class EmployeeConverters extends Generic<Employee, EmployeeDto> {

    public Function<Employee, EmployeeDto> convertEmployeeEntityToDto = convertEntityToDto();
    public Function<EmployeeDto, Employee> convertEmployeeDtoToEntity = convertDtoToEntity();
    public Function<List<Employee>, List<EmployeeDto>> convertEmployeeEntityListToDto = convertListEntitiesToDto();
    public Function<List<EmployeeDto>, List<Employee>> convertEmployeeDtoListToEntities = convertListDtoToEntities();

    @Override
    protected Function<Employee, EmployeeDto> convertEntityToDto() {
        return employee -> EmployeeDto.builder()
            .birthDate(employee.getBirthDate())
            .dateOfEmployment(employee.getDateOfEmployment())
            .department(employee.getDepartment())
            .firstName(employee.getFirstName())
            .grades(employee.getGrades())
            .id(employee.getId())
            .isFired(employee.getIsFired())
            .location(employee.getLocation())
            .project(employee.getProject())
            .salary(employee.getSalary())
            .secondName(employee.getSecondName())
            .build();
    }

    @Override
    protected Function<EmployeeDto, Employee> convertDtoToEntity() {
        return employeeDtp -> Employee.builder()
            .birthDate(employeeDtp.getBirthDate())
            .dateOfEmployment(employeeDtp.getDateOfEmployment())
            .department(employeeDtp.getDepartment())
            .firstName(employeeDtp.getFirstName())
            .grades(employeeDtp.getGrades())
            .id(employeeDtp.getId())
            .isFired(employeeDtp.getIsFired())
            .location(employeeDtp.getLocation())
            .project(employeeDtp.getProject())
            .salary(employeeDtp.getSalary())
            .secondName(employeeDtp.getSecondName())
            .build();
    }

    @Override
    protected Function<List<Employee>, List<EmployeeDto>> convertListEntitiesToDto() {
        return employees -> employees.stream()
            .map(employee -> EmployeeDto.builder()
                .birthDate(employee.getBirthDate())
                .dateOfEmployment(employee.getDateOfEmployment())
                .department(employee.getDepartment())
                .firstName(employee.getFirstName())
                .grades(employee.getGrades())
                .id(employee.getId())
                .isFired(employee.getIsFired())
                .location(employee.getLocation())
                .project(employee.getProject())
                .salary(employee.getSalary())
                .secondName(employee.getSecondName())
                .build())
            .collect(Collectors.toList());
    }

    @Override
    protected Function<List<EmployeeDto>, List<Employee>> convertListDtoToEntities() {
        return employeeDtos -> employeeDtos.stream()
            .map(employeeDto -> Employee.builder()
                .birthDate(employeeDto.getBirthDate())
                .dateOfEmployment(employeeDto.getDateOfEmployment())
                .department(employeeDto.getDepartment())
                .firstName(employeeDto.getFirstName())
                .grades(employeeDto.getGrades())
                .id(employeeDto.getId())
                .isFired(employeeDto.getIsFired())
                .location(employeeDto.getLocation())
                .project(employeeDto.getProject())
                .salary(employeeDto.getSalary())
                .secondName(employeeDto.getSecondName())
                .build())
            .collect(Collectors.toList());
    }

}
