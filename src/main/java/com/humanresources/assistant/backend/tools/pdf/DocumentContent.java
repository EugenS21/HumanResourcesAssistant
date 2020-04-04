package com.humanresources.assistant.backend.tools.pdf;

import static com.humanresources.assistant.backend.tools.other.FileParser.getAboutJob;

import com.humanresources.assistant.backend.enums.Department;
import com.humanresources.assistant.backend.enums.Grade;
import com.humanresources.assistant.backend.tools.other.FileParser;
import com.vaadin.flow.component.select.Select;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DocumentContent {

    private final String header;
    private final String companyDescription;
    private final String jobDescription;
    private final List<String> requirements;
    private final List<String> techStack;
    private final List<String> responsibilities;
    private final List<String> benefits;

    public DocumentContent() {
        this.header = null;
        this.companyDescription = null;
        this.jobDescription = null;
        this.requirements = null;
        this.techStack = null;
        this.responsibilities = null;
        this.benefits = null;
    }

    public DocumentContent(Select<Department> department, Select<Grade> grade) {
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
        } else {
            throw new IllegalArgumentException("One of the provided arguments is null");
        }
    }
}
