package com.humanresources.assistant.backend.tools.pdf;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DocumentContent {

    private final String header;
    private final String companyDescription;
    private final String jobDescription;
    private final List<String> requirements;
    private final List<String> techStack;
    private final List<String> responsibilities;
    private final List<String> benefits;
    private final String footer;
}
