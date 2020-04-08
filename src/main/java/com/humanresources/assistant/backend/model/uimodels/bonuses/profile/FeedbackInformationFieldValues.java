package com.humanresources.assistant.backend.model.uimodels.bonuses.profile;

import com.humanresources.assistant.backend.enums.Department;
import com.humanresources.assistant.backend.enums.Grade;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FeedbackInformationFieldValues {

    private final String firstName;
    private final String lastName;
    private final LocalDateTime localDateTime;
    private final String description;
    private final Grade grade;
    private final Department department;
    private final String project;
}
