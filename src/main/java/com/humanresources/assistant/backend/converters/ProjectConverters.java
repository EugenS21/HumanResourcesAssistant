package com.humanresources.assistant.backend.converters;

import com.humanresources.assistant.backend.dto.ProjectDto;
import com.humanresources.assistant.backend.entity.Project;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectConverters extends Generic<Project, ProjectDto> {

    @Autowired
    ClientConverters clientConverters;

    public Function<List<Project>, List<ProjectDto>> convertProjectEntityListToDto = convertListEntitiesToDto();
    public Function<List<ProjectDto>, List<Project>> convertProjectDtoListToEntity = convertListDtoToEntities();
    public Function<Project, ProjectDto> convertProjectEntityToDto = convertEntityToDto();
    public Function<ProjectDto, Project> convertProjectDtoToEntity = convertDtoToEntity();

    @Override
    protected Function<Project, ProjectDto> convertEntityToDto() {
        return project -> ProjectDto.builder()
            .id(project.getId())
            .client(clientConverters.convertClientEntityToDto.apply(project.getClient()))
            .projectName(project.getProjectName())
            .build();
    }

    @Override
    protected Function<ProjectDto, Project> convertDtoToEntity() {
        return project -> Project.builder()
            .client(clientConverters.convertClientDtoToEntity.apply(project.getClient()))
            .projectName(project.getProjectName())
            .build();
    }

    @Override
    protected Function<List<Project>, List<ProjectDto>> convertListEntitiesToDto() {
        return projects -> projects.stream()
            .map(project -> convertEntityToDto().apply(project))
            .collect(Collectors.toList());
    }

    @Override
    protected Function<List<ProjectDto>, List<Project>> convertListDtoToEntities() {
        return projects -> projects.stream()
            .map(project -> convertDtoToEntity().apply(project))
            .collect(Collectors.toList());
    }

}
