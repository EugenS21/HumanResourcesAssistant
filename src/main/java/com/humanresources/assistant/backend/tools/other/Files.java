package com.humanresources.assistant.backend.tools.other;

import java.io.File;
import lombok.Getter;

public enum Files {
    ABOUT_JOB(getFileFromPath("src/main/resources/jobfiles/AboutTheJob")),
    RESPONSIBILITIES(getFileFromPath("src/main/resources/jobfiles/Responsibilities")),
    REQUIREMENTS(getFileFromPath("src/main/resources/jobfiles/Requirements")),
    TECHNOLOGIES(getFileFromPath("src/main/resources/jobfiles/TechnologiesStack")),
    BENEFITS(getFileFromPath("src/main/resources/jobfiles/CompanyBenefits")),
    COMPANY_DESCRIPTION(getFileFromPath("src/main/resources/jobfiles/CompanyInformation"));

    @Getter
    private final File file;

    Files(File file) {
        this.file = file;
    }

    private static File getFileFromPath(String path) {
        return new File(path);
    }
}
