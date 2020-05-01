package com.humanresources.assistant.backend.converters;

import com.humanresources.assistant.backend.dto.DepartmentDto;
import com.humanresources.assistant.backend.entity.DepartmentEntity;
import com.humanresources.assistant.backend.enums.DepartmentEnum;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class DepartmentConverters extends Generic<DepartmentEntity, DepartmentDto> {

    public Function<List<DepartmentEntity>, List<DepartmentDto>> convertDepartmentEntityListToDto = convertListEntitiesToDto();
    public Function<List<DepartmentDto>, List<DepartmentEntity>> convertDepartmentDtoListToEntity = convertListDtoToEntities();
    public Function<DepartmentEntity, DepartmentDto> convertDepartmentEntityToDto = convertEntityToDto();
    public Function<DepartmentDto, DepartmentEntity> convertDepartmentDtoToEntity = convertDtoToEntity();

    @Override
    protected Function<DepartmentEntity, DepartmentDto> convertEntityToDto() {
        return department -> DepartmentDto.builder()
            .id(department.getId())
            .department(department.getDepartment().getName())
            .build();
    }

    @Override
    protected Function<DepartmentDto, DepartmentEntity> convertDtoToEntity() {
        return department -> DepartmentEntity.builder()
            .department(DepartmentEnum.valueOf(department.getDepartment()))
            .build();
    }

    @Override
    protected Function<List<DepartmentEntity>, List<DepartmentDto>> convertListEntitiesToDto() {
        return departments -> departments.stream()
            .map(department -> convertEntityToDto().apply(department))
            .collect(Collectors.toList());
    }

    @Override
    protected Function<List<DepartmentDto>, List<DepartmentEntity>> convertListDtoToEntities() {
        return departments -> departments.stream()
            .map(department -> convertDtoToEntity().apply(department))
            .collect(Collectors.toList());
    }

}
