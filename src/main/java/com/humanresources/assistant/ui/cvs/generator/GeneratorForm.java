package com.humanresources.assistant.ui.cvs.generator;

import static java.util.Arrays.asList;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import java.util.ArrayList;
import java.util.List;

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
        footerField.setLabel("Custom End");

        companyDescription.setLabel("Company Description");
        aboutTheJob.setLabel("About The Job");
        requirements.setLabel("Requirements List");
        techStack.setLabel("Tech Stack List");
        responsibilities.setLabel("Responsibilities List");
        companyBenefits.setLabel("Company Benefits List");
        initializeTextAreas(new ArrayList<TextArea>() {{
            addAll(asList(companyDescription, aboutTheJob, requirements, techStack, responsibilities, companyBenefits));
        }});
        initializeTextFields(new ArrayList<TextField>() {{add(footerField); add(headerField);}});
    }

    private void initializeTextAreas(List<TextArea> textAreaList) {
        for (TextArea textArea : textAreaList) {
            textArea.setSizeFull();
            textArea.setMinHeight("10px");
            textArea.setMaxHeight("200px");
        }
    }

    private void initializeTextFields(List<TextField> textFieldsList) {
        for (TextField textField : textFieldsList) {
            textField.setSizeFull();
        }
    }
}
