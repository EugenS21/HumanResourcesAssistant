package com.humanresources.assistant.backend.converters;

import com.humanresources.assistant.backend.dto.DepartmentDto;
import com.humanresources.assistant.backend.entity.Department;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class DepartmentConverters extends Generic<Department, DepartmentDto> {

    public Function<List<Department>, List<DepartmentDto>> convertDepartmentEntityListToDto = convertListEntitiesToDto();
    public Function<List<DepartmentDto>, List<Department>> convertDepartmentDtoListToEntity = convertListDtoToEntities();
    public Function<Department, DepartmentDto> convertDepartmentEntityToDto = convertEntityToDto();
    public Function<DepartmentDto, Department> convertDepartmentDtoToEntity = convertDtoToEntity();

    @Override
    protected Function<Department, DepartmentDto> convertEntityToDto() {
        return department -> DepartmentDto.builder()
            .id(department.getId())
            .name(department.getName())
            .build();
    }

    @Override
    protected Function<DepartmentDto, Department> convertDtoToEntity() {
        return department -> Department.builder()
            .name(department.getName())
            .build();
    }

    @Override
    protected Function<List<Department>, List<DepartmentDto>> convertListEntitiesToDto() {
        return departments -> departments.stream()
            .map(department -> convertEntityToDto().apply(department))
            .collect(Collectors.toList());
    }

    @Override
    protected Function<List<DepartmentDto>, List<Department>> convertListDtoToEntities() {
        return departments -> departments.stream()
            .map(department -> convertDtoToEntity().apply(department))
            .collect(Collectors.toList());
    }

}
