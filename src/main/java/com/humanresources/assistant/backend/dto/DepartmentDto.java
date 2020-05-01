package com.humanresources.assistant.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.humanresources.assistant.backend.entity.EmployeeEntity;
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

    @JsonProperty ("managerId")
    private EmployeeEntity managerId;

    @JsonProperty ("startingSalary")
    private Integer startingSalary;

}
