package com.humanresources.assistant.sshclients.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.humanresources.assistant.restclient.multipart.MultipartFile;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
@JsonIgnoreType
public class EmployeeFile {

    private final String fileName;

    @JsonIgnore
    private final MultipartFile multipartFile;
}
