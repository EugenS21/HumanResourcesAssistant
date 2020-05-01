package com.humanresources.assistant.backend.converters;

import com.humanresources.assistant.backend.dto.GradeDto;
import com.humanresources.assistant.backend.entity.GradeEntity;
import com.humanresources.assistant.backend.enums.GradeEnum;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class GradeConverters extends Generic<GradeEntity, GradeDto> {

    public Function<List<GradeEntity>, List<GradeDto>> convertGradeEntityListToDto = convertListEntitiesToDto();
    public Function<List<GradeDto>, List<GradeEntity>> convertGradeDtoListToEntity = convertListDtoToEntities();
    public Function<GradeEntity, GradeDto> convertGradeEntityToDto = convertEntityToDto();
    public Function<GradeDto, GradeEntity> convertGradeDtoToEntity = convertDtoToEntity();

    @Override
    public Function<GradeEntity, GradeDto> convertEntityToDto() {
        return gradeEntity -> GradeDto.builder()
            .id(gradeEntity.getId())
            .grade(gradeEntity.getGrade().getName())
            .build();
    }

    @Override
    public Function<GradeDto, GradeEntity> convertDtoToEntity() {
        return grade -> GradeEntity.builder()
            .grade(GradeEnum.valueOf(grade.getGrade()))
            .build();
    }

    @Override
    public Function<List<GradeEntity>, List<GradeDto>> convertListEntitiesToDto() {
        return grades -> grades.stream()
            .map(gradeEntity -> convertEntityToDto().apply(gradeEntity))
            .collect(Collectors.toList());
    }

    @Override
    public Function<List<GradeDto>, List<GradeEntity>> convertListDtoToEntities() {
        return grades -> grades.stream()
            .map(grade -> convertDtoToEntity().apply(grade))
            .collect(Collectors.toList());
    }

}
