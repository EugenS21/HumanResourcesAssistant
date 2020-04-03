package com.humanresources.assistant.ui.cvs.generator;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

public class GeneratorForm extends VerticalLayout {

    private final TextField headerField;
    private final TextArea companyDescription;
    private final TextArea aboutTheJob;
    private final TextArea requirements;
    private final TextArea techStack;
    private final TextArea responsibilities;
    private final TextArea companyBenefits;
    private final TextField footerField;
    private final Button applyButton;

    public GeneratorForm() {
        setWidth("800px");
        headerField = new TextField();
        companyDescription = new TextArea();
        aboutTheJob = new TextArea();
        requirements = new TextArea();
        techStack = new TextArea();
        responsibilities = new TextArea();
        companyBenefits = new TextArea();
        footerField = new TextField();
        applyButton = new Button();

        initializeFormFields();

        add(headerField, companyDescription, aboutTheJob, requirements,
            techStack, responsibilities, companyBenefits, footerField, applyButton);
    }

    public void setFormContent(GeneratorFormData formFieldsData) {
        this.headerField.setValue(formFieldsData.getHeaderFieldValue());
        this.companyDescription.setValue(formFieldsData.getCompanyDescriptionValue());
        this.aboutTheJob.setValue(formFieldsData.getAboutTheJobValue());
        this.requirements.setValue(formFieldsData.getRequirementsValue());
        this.techStack.setValue(formFieldsData.getTechStackValue());
        this.responsibilities.setValue(formFieldsData.getResponsibilitiesValue());
        this.companyBenefits.setValue(formFieldsData.getCompanyBenefitsValue());
        this.footerField.setValue(formFieldsData.getFooterFieldValue());
    }

    private void initializeFormFields() {
        applyButton.setText("Apply Changes");
        applyButton.setSizeFull();
        headerField.setLabel("Header");
        headerField.setSizeFull();
        companyDescription.setLabel("Company Description");
        companyDescription.setSizeFull();
        aboutTheJob.setLabel("About The Job");
        aboutTheJob.setSizeFull();
        requirements.setLabel("Requirements List");
        requirements.setSizeFull();
        techStack.setLabel("Tech Stack List");
        techStack.setSizeFull();
        responsibilities.setLabel("Responsibilities List");
        responsibilities.setSizeFull();
        companyBenefits.setLabel("Company Benefits List");
        companyBenefits.setSizeFull();
        footerField.setLabel("Custom End");
        footerField.setSizeFull();
    }
}
