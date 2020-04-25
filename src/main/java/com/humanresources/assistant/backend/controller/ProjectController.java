package com.humanresources.assistant.backend.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.humanresources.assistant.backend.dto.ProjectDto;
import com.humanresources.assistant.backend.service.ProjectService;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectController {

    private final Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    ProjectService projectService;

    @GetMapping (value = "/project", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize ("hasAnyAuthority('hr','admin')")
    public ResponseEntity<List<ProjectDto>> getAllProjects() {
        return ResponseEntity.ok()
            .body(projectService.getAllProjects());
    }

    @PostMapping (value = "/project", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize ("hasAuthority('admin')")
    public ResponseEntity<ProjectDto> postNewProject(@Valid @RequestBody ProjectDto project) {
        try {
            projectService.saveProject(project);
            return ResponseEntity.ok().body(project);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(project);
        }
    }

    @PutMapping (value = "/project/{id}", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize ("hasAuthority('admin')")
    public ResponseEntity<ProjectDto> putProject(@PathVariable (name = "id") Integer id,
        @Valid @RequestBody ProjectDto projectDto) {
        try {
            projectService.updateProject(id, projectDto);
            return ResponseEntity.ok().body(projectDto);
        } catch (Exception e) {
            logger.error("PUT request could not be executed {}", e);
            return ResponseEntity.badRequest().body(projectDto);
        }
    }

    @DeleteMapping (value = "/project/{id}")
    @PreAuthorize ("hasAuthority('admin')")
    public ResponseEntity<String> deleteProject(@PathVariable (name = "id") @Valid Integer id) {
        try {
            projectService.deleteProject(id);
            return ResponseEntity.ok().body("Project was removed");
        } catch (Exception e) {
            logger.info("DELETE request could not be executed {}", e);
            return ResponseEntity.badRequest().body("Could not remove");
        }
    }

}
