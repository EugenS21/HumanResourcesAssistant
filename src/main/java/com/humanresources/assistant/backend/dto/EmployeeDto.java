package com.humanresources.assistant.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.humanresources.assistant.backend.entity.EmployeeEntity;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {

    @JsonProperty ("id")
    private Integer id;

    @JsonProperty ("firstName")
    private String firstName;

    @JsonProperty ("secondName")
    private String secondName;

    @JsonProperty ("birthDate")
    @JsonSerialize (using = LocalDateSerializer.class)
    @JsonDeserialize (using = LocalDateDeserializer.class)
    private LocalDate birthDate;

    @JsonProperty ("dateOfEmployment")
    @JsonSerialize (using = LocalDateSerializer.class)
    @JsonDeserialize (using = LocalDateDeserializer.class)
    private LocalDate dateOfEmployment;

    @JsonProperty ("salary")
    private Integer salary;

    @JsonProperty ("isFired")
    private Boolean isFired;

    @JsonProperty ("user")
    private UserDto user;

    @JsonProperty ("department")
    private DepartmentDto department;

    @JsonProperty ("location")
    private LocationDto location;

    @JsonProperty ("project")
    private ProjectDto project;

    @JsonProperty ("grades")
    private GradeDto grade;

    @JsonProperty ("teamLead")
    private EmployeeEntity teamLead;

}
