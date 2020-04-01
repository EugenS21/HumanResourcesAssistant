package com.humanresources.assistant.backend.model;

import com.humanresources.assistant.backend.enums.Department;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Setter
@Getter
public class CurriculumVitae {

    private Long id;
    private String fileName;
    private String userDetails;
    private String userPhoneNumber;
    private Department jobToApply;
    private String description;
}
