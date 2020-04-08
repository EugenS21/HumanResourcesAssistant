package com.humanresources.assistant.backend.model.uimodels.bonuses.profile;

import com.humanresources.assistant.backend.enums.Department;
import com.humanresources.assistant.backend.enums.Grade;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GeneralInformationFieldValues {

    private String name;
    private String surname;
    private String phone;
    private String email;
    private String address;
    private String project;
    private Department department;
    private Grade grade;
}
