package com.humanresources.assistant.backend.converters;

import com.humanresources.assistant.backend.dto.EmployeeDto;
import com.humanresources.assistant.backend.entity.Employee;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeConverters extends Generic<Employee, EmployeeDto> {

    @Autowired
    DepartmentConverters departmentConverters;

    @Autowired
    GradeConverters gradeConverters;

    @Autowired
    LocationConverters locationConverters;

    @Autowired
    ProjectConverters projectConverters;

    @Autowired
    UserConverters userConverters;

    public Function<Employee, EmployeeDto> convertEmployeeEntityToDto = convertEntityToDto();
    public Function<EmployeeDto, Employee> convertEmployeeDtoToEntity = convertDtoToEntity();
    public Function<List<Employee>, List<EmployeeDto>> convertEmployeeEntityListToDto = convertListEntitiesToDto();
    public Function<List<EmployeeDto>, List<Employee>> convertEmployeeDtoListToEntities = convertListDtoToEntities();

    @Override
    protected Function<Employee, EmployeeDto> convertEntityToDto() {
        return employee -> EmployeeDto.builder()
            .birthDate(employee.getBirthDate())
            .dateOfEmployment(employee.getDateOfEmployment())
            .department(departmentConverters.convertDepartmentEntityToDto.apply(employee.getDepartment()))
            .firstName(employee.getFirstName())
            .grade(gradeConverters.convertGradeEntityToDto.apply(employee.getGrades()))
            .id(employee.getId())
            .isFired(employee.getIsFired())
            .location(locationConverters.convertLocationEntityToDto.apply(employee.getLocation()))
            .project(projectConverters.convertProjectEntityToDto.apply(employee.getProject()))
            .salary(employee.getSalary())
            .secondName(employee.getSecondName())
            .user(userConverters.convertUserEntityToDto.apply(employee.getUser()))
            .build();
    }

    @Override
    protected Function<EmployeeDto, Employee> convertDtoToEntity() {
        return employeeDto -> Employee.builder()
            .birthDate(employeeDto.getBirthDate())
            .dateOfEmployment(employeeDto.getDateOfEmployment())
            .department(departmentConverters.convertDtoToEntity().apply(employeeDto.getDepartment()))
            .firstName(employeeDto.getFirstName())
            .grades(gradeConverters.convertGradeDtoToEntity.apply(employeeDto.getGrade()))
            .id(employeeDto.getId())
            .isFired(employeeDto.getIsFired())
            .location(locationConverters.convertLocationDtoToEntity.apply(employeeDto.getLocation()))
            .project(projectConverters.convertProjectDtoToEntity.apply(employeeDto.getProject()))
            .salary(employeeDto.getSalary())
            .secondName(employeeDto.getSecondName())
            .user(userConverters.convertUserDtoToEntity.apply(employeeDto.getUser()))
            .build();
    }

    @Override
    protected Function<List<Employee>, List<EmployeeDto>> convertListEntitiesToDto() {
        return employees -> employees.stream()
            .map(employee -> convertEmployeeEntityToDto.apply(employee))
            .collect(Collectors.toList());
    }

    @Override
    protected Function<List<EmployeeDto>, List<Employee>> convertListDtoToEntities() {
        return employees -> employees.stream()
            .map(employeeDto -> convertEmployeeDtoToEntity.apply(employeeDto))
            .collect(Collectors.toList());
    }

}
