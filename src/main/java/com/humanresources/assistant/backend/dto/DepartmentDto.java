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
public class DepartmentDto {

    @JsonProperty ("id")
    private Integer id;

    @JsonProperty ("department")
    private String department;

}
