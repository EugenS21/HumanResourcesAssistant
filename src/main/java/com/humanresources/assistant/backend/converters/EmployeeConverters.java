package com.humanresources.assistant.backend.converters;

import com.humanresources.assistant.backend.dto.EmployeeDto;
import com.humanresources.assistant.backend.entity.EmployeeEntity;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeConverters extends Generic<EmployeeEntity, EmployeeDto> {

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

    public Function<EmployeeEntity, EmployeeDto> convertEmployeeEntityToDto = convertEntityToDto();
    public Function<EmployeeDto, EmployeeEntity> convertEmployeeDtoToEntity = convertDtoToEntity();
    public Function<List<EmployeeEntity>, List<EmployeeDto>> convertEmployeeEntityListToDto = convertListEntitiesToDto();
    public Function<List<EmployeeDto>, List<EmployeeEntity>> convertEmployeeDtoListToEntities = convertListDtoToEntities();

    @Override
    protected Function<EmployeeEntity, EmployeeDto> convertEntityToDto() {
        return employee -> EmployeeDto.builder()
            .birthDate(employee.getBirthDate())
            .dateOfEmployment(employee.getDateOfEmployment())
            .department(departmentConverters.convertDepartmentEntityToDto.apply(employee.getDepartmentEntity()))
            .firstName(employee.getFirstName())
            .grade(gradeConverters.convertGradeEntityToDto.apply(employee.getGradeEntity()))
            .id(employee.getId())
            .isFired(employee.getIsFired())
            .location(locationConverters.convertLocationEntityToDto.apply(employee.getLocationEntity()))
            .project(projectConverters.convertProjectEntityToDto.apply(employee.getProjectEntity()))
            .salary(employee.getSalary())
            .secondName(employee.getSecondName())
            .user(userConverters.convertUserEntityToDto.apply(employee.getUser()))
            .build();
    }

    @Override
    protected Function<EmployeeDto, EmployeeEntity> convertDtoToEntity() {
        return employeeDto -> EmployeeEntity.builder()
            .birthDate(employeeDto.getBirthDate())
            .dateOfEmployment(employeeDto.getDateOfEmployment())
            .departmentEntity(departmentConverters.convertDtoToEntity().apply(employeeDto.getDepartment()))
            .firstName(employeeDto.getFirstName())
            .gradeEntity(gradeConverters.convertGradeDtoToEntity.apply(employeeDto.getGrade()))
            .id(employeeDto.getId())
            .isFired(employeeDto.getIsFired())
            .locationEntity(locationConverters.convertLocationDtoToEntity.apply(employeeDto.getLocation()))
            .projectEntity(projectConverters.convertProjectDtoToEntity.apply(employeeDto.getProject()))
            .salary(employeeDto.getSalary())
            .secondName(employeeDto.getSecondName())
            .user(userConverters.convertUserDtoToEntity.apply(employeeDto.getUser()))
            .build();
    }

    @Override
    protected Function<List<EmployeeEntity>, List<EmployeeDto>> convertListEntitiesToDto() {
        return employees -> employees.stream()
            .map(employee -> convertEmployeeEntityToDto.apply(employee))
            .collect(Collectors.toList());
    }

    @Override
    protected Function<List<EmployeeDto>, List<EmployeeEntity>> convertListDtoToEntities() {
        return employees -> employees.stream()
            .map(employeeDto -> convertEmployeeDtoToEntity.apply(employeeDto))
            .collect(Collectors.toList());
    }

}
