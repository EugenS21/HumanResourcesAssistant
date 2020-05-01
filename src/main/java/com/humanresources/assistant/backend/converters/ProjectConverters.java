package com.humanresources.assistant.backend.converters;

import com.humanresources.assistant.backend.dto.ProjectDto;
import com.humanresources.assistant.backend.entity.ProjectEntity;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectConverters extends Generic<ProjectEntity, ProjectDto> {

    @Autowired
    ClientConverters clientConverters;

    public Function<List<ProjectEntity>, List<ProjectDto>> convertProjectEntityListToDto = convertListEntitiesToDto();
    public Function<List<ProjectDto>, List<ProjectEntity>> convertProjectDtoListToEntity = convertListDtoToEntities();
    public Function<ProjectEntity, ProjectDto> convertProjectEntityToDto = convertEntityToDto();
    public Function<ProjectDto, ProjectEntity> convertProjectDtoToEntity = convertDtoToEntity();

    @Override
    protected Function<ProjectEntity, ProjectDto> convertEntityToDto() {
        return project -> ProjectDto.builder()
            .id(project.getId())
            .client(clientConverters.convertClientEntityToDto.apply(project.getClientEntity()))
            .projectName(project.getProjectName())
            .build();
    }

    @Override
    protected Function<ProjectDto, ProjectEntity> convertDtoToEntity() {
        return project -> ProjectEntity.builder()
            .clientEntity(clientConverters.convertClientDtoToEntity.apply(project.getClient()))
            .projectName(project.getProjectName())
            .build();
    }

    @Override
    protected Function<List<ProjectEntity>, List<ProjectDto>> convertListEntitiesToDto() {
        return projects -> projects.stream()
            .map(project -> convertEntityToDto().apply(project))
            .collect(Collectors.toList());
    }

    @Override
    protected Function<List<ProjectDto>, List<ProjectEntity>> convertListDtoToEntities() {
        return projects -> projects.stream()
            .map(project -> convertDtoToEntity().apply(project))
            .collect(Collectors.toList());
    }

}
