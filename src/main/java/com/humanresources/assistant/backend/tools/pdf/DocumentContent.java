package com.humanresources.assistant.backend.tools.pdf;

import static com.humanresources.assistant.backend.tools.other.FileParser.getAboutJob;

import com.humanresources.assistant.backend.enums.DepartmentEnum;
import com.humanresources.assistant.backend.enums.GradeEnum;
import com.humanresources.assistant.backend.tools.other.FileParser;
import com.vaadin.flow.component.select.Select;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DocumentContent {

    private static final String FOOTER = "WE ARE WAITING FOR YOU!";

    private String header;
    private String companyDescription;
    private String jobDescription;
    private List<String> requirements;
    private List<String> techStack;
    private List<String> responsibilities;
    private List<String> benefits;
    private String customEnd;

    public DocumentContent() {
        this.header = null;
        this.companyDescription = null;
        this.jobDescription = null;
        this.requirements = null;
        this.techStack = null;
        this.responsibilities = null;
        this.benefits = null;
    }

    public DocumentContent(Select<DepartmentEnum> department, Select<GradeEnum> grade) {
        if (department != null && grade != null) {
            final String gradeName = grade.getValue().getName().toUpperCase();
            final String departmentName = department.getValue().getName().toUpperCase();
            this.header = gradeName + " " + departmentName;
            this.jobDescription = getAboutJob(grade.getValue(), department.getValue());
            this.requirements = FileParser.getRequirements(grade.getValue(), department.getValue());
            this.responsibilities = FileParser.getResponsibilities(grade.getValue(), department.getValue());
            this.techStack = FileParser.getTechStack(department.getValue());
            this.benefits = FileParser.getBenefits();
            this.companyDescription = FileParser.getCompanyDescription();
            this.customEnd = FOOTER;
        } else {
            throw new IllegalArgumentException("One of the provided arguments is null");
        }
    }
}
