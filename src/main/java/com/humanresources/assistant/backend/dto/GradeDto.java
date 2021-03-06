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
public class GradeDto {

    @JsonProperty ("id")
    private Integer id;

    @JsonProperty ("grade")
    private String grade;

    @JsonProperty ("additionToSalary")
    private Double additionToSalary;

}
