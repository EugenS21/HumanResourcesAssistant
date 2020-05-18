package com.humanresources.assistant.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.File;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class FileDto {

    @JsonProperty ("id")
    private Long id;

    @JsonProperty ("fileName")
    private String fileName;

    @JsonProperty ("fileType")
    private String fileType;

    @JsonProperty ("phoneNumber")
    private String phoneNumber;

    @JsonProperty ("department")
    private String department;

    @JsonProperty ("description")
    private String description;

    @JsonProperty ("user")
    private UserDto user;

    @JsonIgnore
    private File multipartFile;

}
