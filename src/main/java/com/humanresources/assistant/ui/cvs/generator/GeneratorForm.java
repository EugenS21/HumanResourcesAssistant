package com.humanresources.assistant.ui.cvs.generator;

import static com.humanresources.assistant.backend.functions.GenericFunctions.getListFromString;
import static java.util.Arrays.asList;

import com.humanresources.assistant.backend.tools.pdf.DocumentContent;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasValue.ValueChangeListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextAreaVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

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
    private final ArrayList<TextArea> textAreaList;
    private final ArrayList<TextField> textFieldsList;

    @Getter
    private final DocumentContent documentContent;
    @Setter
    private Dialog currentDialog;

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
        documentContent = new DocumentContent();
        textAreaList = new ArrayList<TextArea>() {{
            addAll(asList(companyDescription, aboutTheJob, requirements, techStack, responsibilities, companyBenefits));
        }};
        textFieldsList = new ArrayList<TextField>() {{
            add(footerField);
            add(headerField);
        }};
        initializeFormFields();

        add(headerField, companyDescription, aboutTheJob,
            requirements, techStack, responsibilities,
            companyBenefits, footerField, applyButton);
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
        applyButton.addClickListener(applyButtonAction());
        applyButton.setEnabled(false);

        headerField.setLabel("Header");
        headerField.addValueChangeListener(getHeaderChange());
        footerField.setLabel("Custom End");
        footerField.addValueChangeListener(getCustomEndChange());

        companyDescription.setLabel("Company Description");
        companyDescription.addValueChangeListener(getCompanyDescriptionChange());
        aboutTheJob.setLabel("About The Job");
        aboutTheJob.addValueChangeListener(getAboutTheJobChange());
        requirements.setLabel("Requirements List");
        requirements.addValueChangeListener(getRequirementsChange());
        techStack.setLabel("Tech Stack List");
        techStack.addValueChangeListener(getTechStackChange());
        responsibilities.setLabel("Responsibilities List");
        responsibilities.addValueChangeListener(getResponsibilitiesChange());
        companyBenefits.setLabel("Company Benefits List");
        companyBenefits.addValueChangeListener(getBenefitsChange());

        initializeTextAreas(textAreaList);

        initializeTextFields(textFieldsList);
    }

    private void initializeTextAreas(List<TextArea> textAreaList) {
        for (TextArea textArea : textAreaList) {
            textArea.setSizeFull();
            textArea.addThemeVariants(TextAreaVariant.LUMO_SMALL);
            textArea.setMinHeight("10px");
            textArea.setMaxHeight("150px");
            textArea.setErrorMessage("Can't be empty!");
            textArea.setRequired(true);
        }
    }

    private void initializeTextFields(List<TextField> textFieldsList) {
        for (TextField textField : textFieldsList) {
            textField.setSizeFull();
            textField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
            textField.setErrorMessage("Can't be empty!");
            textField.setRequired(true);
        }
    }

    private ComponentEventListener<ClickEvent<Button>> applyButtonAction() {
        return (ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
            if (checkComponentsIntegrity()) {
                Generator.isDialogOpen = true;
                currentDialog.close();
            } else {
                applyButton.setEnabled(false);
            }
        };
    }

    private ValueChangeListener<? super ComponentValueChangeEvent<TextField, String>> getHeaderChange() {
        return (ValueChangeListener<? super ComponentValueChangeEvent<TextField, String>>) valueChange -> {
            documentContent.setHeader(headerField.getValue());
            if (!headerField.isEmpty()) {
                enableButton();
            }
        };
    }

    private ValueChangeListener<? super ComponentValueChangeEvent<TextField, String>> getCustomEndChange() {
        return (ValueChangeListener<? super ComponentValueChangeEvent<TextField, String>>) valueChange -> {
            documentContent.setCustomEnd(footerField.getValue());
            if (!footerField.isEmpty()) {
                enableButton();
            }
        };
    }

    private ValueChangeListener<? super ComponentValueChangeEvent<TextArea, String>> getCompanyDescriptionChange() {
        return (ValueChangeListener<? super ComponentValueChangeEvent<TextArea, String>>) valueChange -> {
            documentContent.setCompanyDescription(companyDescription.getValue());
            if (!companyDescription.isEmpty()) {
                enableButton();
            }
        };
    }

    private ValueChangeListener<? super ComponentValueChangeEvent<TextArea, String>> getAboutTheJobChange() {
        return (ValueChangeListener<? super ComponentValueChangeEvent<TextArea, String>>) valueChange -> {
            documentContent.setJobDescription(aboutTheJob.getValue());
            if (!aboutTheJob.isEmpty()) {
                enableButton();
            }
        };
    }

    private ValueChangeListener<? super ComponentValueChangeEvent<TextArea, String>> getRequirementsChange() {
        return (ValueChangeListener<? super ComponentValueChangeEvent<TextArea, String>>) valueChange -> {
            documentContent.setRequirements(getListFromString.apply(requirements.getValue()));
            if (!requirements.isEmpty()) {
                enableButton();
            }
        };
    }

    private ValueChangeListener<? super ComponentValueChangeEvent<TextArea, String>> getTechStackChange() {
        return (ValueChangeListener<? super ComponentValueChangeEvent<TextArea, String>>) valueChange -> {
            documentContent.setTechStack(getListFromString.apply(techStack.getValue()));
            if (!techStack.isEmpty()) {
                enableButton();
            }
        };
    }

    private ValueChangeListener<? super ComponentValueChangeEvent<TextArea, String>> getResponsibilitiesChange() {
        return (ValueChangeListener<? super ComponentValueChangeEvent<TextArea, String>>) valueChange -> {
            documentContent.setResponsibilities(getListFromString.apply(responsibilities.getValue()));
            if (!responsibilities.isEmpty()) {
                enableButton();
            }
        };
    }

    private ValueChangeListener<? super ComponentValueChangeEvent<TextArea, String>> getBenefitsChange() {
        return (ValueChangeListener<? super ComponentValueChangeEvent<TextArea, String>>) valueChange -> {
            documentContent.setBenefits(getListFromString.apply(companyBenefits.getValue()));
            if (!companyBenefits.isEmpty()) {
                enableButton();
            }
        };
    }

    private void enableButton() {
        if (!applyButton.isEnabled()) {
            applyButton.setEnabled(true);
        }
    }

    private boolean checkComponentsIntegrity() {
        return textAreaList.stream().noneMatch(AbstractField::isEmpty)
            && textFieldsList.stream().noneMatch(AbstractField::isEmpty);
    }
}
