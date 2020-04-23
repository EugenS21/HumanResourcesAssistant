package com.humanresources.assistant.backend.dto;

import com.humanresources.assistant.backend.entity.Department;
import com.humanresources.assistant.backend.entity.Grade;
import com.humanresources.assistant.backend.entity.Location;
import com.humanresources.assistant.backend.entity.Project;
import com.humanresources.assistant.backend.entity.authentication.User;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EmployeeDto {

    private final Integer id;

    private final String firstName;

    private final String secondName;

    private final LocalDate birthDate;

    private final LocalDate dateOfEmployment;

    private final Integer salary;

    private final Boolean isFired;

    private final User user;

    private final Department department;

    private final Location location;

    private final Project project;

    private final Grade grades;

}
