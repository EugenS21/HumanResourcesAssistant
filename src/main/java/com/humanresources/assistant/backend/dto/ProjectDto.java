package com.humanresources.assistant.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProjectDto {

    @JsonProperty ("id")
    private Long id;

    @JsonProperty ("projectName")
    private String projectName;

    @JsonProperty ("client")
    private ClientDto client;

}
