package com.humanresources.assistant.backend.service;

import static com.google.common.collect.Lists.newArrayList;

import com.humanresources.assistant.backend.converters.ClientConverters;
import com.humanresources.assistant.backend.converters.ProjectConverters;
import com.humanresources.assistant.backend.dto.ProjectDto;
import com.humanresources.assistant.backend.entity.ProjectEntity;
import com.humanresources.assistant.backend.exceptions.ProjectNotFound;
import com.humanresources.assistant.backend.repository.ProjectRepository;
import java.util.List;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    ProjectConverters projectConverters;

    @Autowired
    ClientConverters clientConverters;

    public List<ProjectDto> getAllProjects() {
        return
            projectConverters.convertProjectEntityListToDto.apply(newArrayList(projectRepository.findAll()));
    }

    public void saveProject(ProjectDto project) {
        projectRepository.save(projectConverters.convertProjectDtoToEntity.apply(project));
    }

    @SneakyThrows
    public ProjectDto updateProject(Integer id, ProjectDto projectDto) {
        final ProjectEntity foundProjectEntity = projectRepository.findById(id).orElseThrow(ProjectNotFound::new);
        foundProjectEntity.setProjectName(projectDto.getProjectName());
        foundProjectEntity.setClientEntity(clientConverters.convertClientDtoToEntity.apply(projectDto.getClient()));
        projectRepository.save(foundProjectEntity);
        return projectConverters.convertProjectEntityToDto.apply(foundProjectEntity);
    }

    public void deleteProject(Integer id) {
        projectRepository.deleteById(id);
    }
}
