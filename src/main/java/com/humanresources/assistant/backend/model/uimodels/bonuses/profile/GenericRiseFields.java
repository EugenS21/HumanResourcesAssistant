package com.humanresources.assistant.backend.model.uimodels.bonuses.profile;

import com.humanresources.assistant.backend.enums.Department;
import com.humanresources.assistant.backend.enums.Grade;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class GenericRiseFields {

    private final String firstName;
    private final String lastName;
    private final LocalDateTime localDateTime;
    private final Grade grade;
    private final Department department;
    private final String project;

    public String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }

    public String getFullJobData() {
        return String.format("%s %s", grade.getName(), department.getName());
    }
}
