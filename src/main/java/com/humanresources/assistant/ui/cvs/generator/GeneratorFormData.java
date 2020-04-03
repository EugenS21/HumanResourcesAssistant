package com.humanresources.assistant.ui.cvs.generator;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GeneratorFormData {

    private final String headerFieldValue;
    private final String companyDescriptionValue;
    private final String aboutTheJobValue;
    private final String requirementsValue;
    private final String techStackValue;
    private final String responsibilitiesValue;
    private final String companyBenefitsValue;
    private final String footerFieldValue;
}
