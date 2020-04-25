package com.humanresources.assistant.backend.converters;

import com.humanresources.assistant.backend.dto.GradeDto;
import com.humanresources.assistant.backend.entity.Grade;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class GradeConverters extends Generic<Grade, GradeDto> {

    public Function<List<Grade>, List<GradeDto>> convertGradeEntityListToDto = convertListEntitiesToDto();
    public Function<List<GradeDto>, List<Grade>> convertGradeDtoListToEntity = convertListDtoToEntities();
    public Function<Grade, GradeDto> convertGradeEntityToDto = convertEntityToDto();
    public Function<GradeDto, Grade> convertGradeDtoToEntity = convertDtoToEntity();

    @Override
    public Function<Grade, GradeDto> convertEntityToDto() {
        return grade -> GradeDto.builder()
            .id(grade.getId())
            .name(grade.getName())
            .build();
    }

    @Override
    public Function<GradeDto, Grade> convertDtoToEntity() {
        return grade -> Grade.builder()
            .name(grade.getName())
            .build();
    }

    @Override
    public Function<List<Grade>, List<GradeDto>> convertListEntitiesToDto() {
        return grades -> grades.stream()
            .map(grade -> convertEntityToDto().apply(grade))
            .collect(Collectors.toList());
    }

    @Override
    public Function<List<GradeDto>, List<Grade>> convertListDtoToEntities() {
        return grades -> grades.stream()
            .map(grade -> convertDtoToEntity().apply(grade))
            .collect(Collectors.toList());
    }

}
