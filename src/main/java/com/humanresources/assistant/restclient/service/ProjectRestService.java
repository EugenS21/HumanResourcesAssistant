package com.humanresources.assistant.restclient.service;

import com.humanresources.assistant.backend.dto.ProjectDto;
import com.humanresources.assistant.restclient.CommonService;
import org.springframework.stereotype.Service;

@Service
public class ProjectRestService extends CommonService<ProjectDto> {

    public static final String PROJECT = "project";

    @Override
    public String getURI() {
        return PROJECT;
    }

    @Override
    public Class<ProjectDto> getResponseClass() {
        return ProjectDto.class;
    }
}
