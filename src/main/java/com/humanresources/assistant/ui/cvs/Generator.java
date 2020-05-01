package com.humanresources.assistant.ui.cvs;

import static com.humanresources.assistant.backend.functions.GenericFunctions.getStringFromList;

import com.humanresources.assistant.backend.enums.DepartmentEnum;
import com.humanresources.assistant.backend.enums.GradeEnum;
import com.humanresources.assistant.backend.tools.pdf.DocumentContent;
import com.humanresources.assistant.backend.tools.pdf.PdfFileGenerator;
import com.humanresources.assistant.backend.tools.pdf.PdfPreviewer;
import com.humanresources.assistant.ui.MainLayout;
import com.humanresources.assistant.ui.cvs.generator.GeneratorForm;
import com.humanresources.assistant.ui.cvs.generator.GeneratorFormData;
import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasValue.ValueChangeListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.dialog.GeneratedVaadinDialog.OpenedChangeEvent;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.BeforeLeaveEvent;
import com.vaadin.flow.router.BeforeLeaveObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Route (value = "cvs_generator", layout = MainLayout.class)
@PageTitle ("CV generator")
public class Generator extends HorizontalLayout implements BeforeLeaveObserver {

    private final Logger logger = LoggerFactory.getLogger(Generator.class);
    public static final String VIEW_NAME = "CVs Generator";

    public static boolean isDialogOpen = false;
    private final Select<DepartmentEnum> department;
    private final Select<GradeEnum> grade;
    private final Button generate;
    private final Button changeBody;
    private final Details details;
    private final VerticalLayout optionsLayout;
    private final VerticalLayout previewLayout;
    private final Dialog dialog;

    private final List<File> temporaryFiles;
    private final PdfPreviewer pdfPreviewer;
    private final GeneratorForm generatorForm;
    private DocumentContent documentContent;

    public Generator() {
        setSizeFull();
        getStyle().set("padding", "10px");
        optionsLayout = new VerticalLayout();
        previewLayout = new VerticalLayout();
        generate = new Button();
        department = new Select<>();
        grade = new Select<>();
        details = new Details();
        changeBody = new Button();
        dialog = new Dialog();

        temporaryFiles = new ArrayList<>();
        pdfPreviewer = new PdfPreviewer();
        generatorForm = new GeneratorForm();
        generatorForm.setCurrentDialog(dialog);

        initializeFrontEndComponents();
        initializeDialog();

        previewLayout.add(pdfPreviewer);
        optionsLayout.add(details, department, grade, generate, changeBody, dialog);
        add(optionsLayout, previewLayout);
    }

    private void initializeFrontEndComponents() {
        optionsLayout.setAlignItems(Alignment.CENTER);
        previewLayout.setAlignItems(Alignment.CENTER);
        generate.setText("Generate CV");
        generate.setEnabled(false);
        changeBody.setText("Change Components");
        changeBody.setEnabled(false);
        department.setItems(DepartmentEnum.values());
        department.setLabel("Department");
        department.setEmptySelectionAllowed(false);
        grade.setLabel("Grade");
        grade.setItems(GradeEnum.values());
        grade.setEmptySelectionAllowed(false);
        details.setSummaryText("Welcome to our CVs generator");
        details.addThemeVariants(DetailsVariant.REVERSE, DetailsVariant.FILLED);
        details.addContent(new ListItem("Select option from the existing selections;"));
        details.addContent(new ListItem("Please on the generate button;"));
        details.addContent(new ListItem("Wait for the CV to be prepared and check the right side;"));
        details.addContent(new ListItem("Share the pdf document to all the available services."));
        generate.addClickListener(generateAndDisplayPdf());
        changeBody.addClickListener(openEditor());
        grade.addValueChangeListener(onGradeChange());
        department.addValueChangeListener(onDepartmentChange());
    }

    private void initializeDialog() {
        dialog.setCloseOnOutsideClick(false);
        dialog.setCloseOnEsc(false);
        dialog.add(generatorForm);
        dialog.addOpenedChangeListener(onDialogClose());
    }

    private ValueChangeListener<? super ComponentValueChangeEvent<Select<DepartmentEnum>, DepartmentEnum>> onDepartmentChange() {
        return (ValueChangeListener<? super ComponentValueChangeEvent<Select<DepartmentEnum>, DepartmentEnum>>) selectionChange -> {
            if (grade.getValue() != null) {
                buildDocumentData();
                generate.setEnabled(true);
            }
        };
    }

    private ValueChangeListener<? super ComponentValueChangeEvent<Select<GradeEnum>, GradeEnum>> onGradeChange() {
        return (ValueChangeListener<? super ComponentValueChangeEvent<Select<GradeEnum>, GradeEnum>>) selectionChange -> {
            if (department.getValue() != null) {
                buildDocumentData();
                generate.setEnabled(true);
            }
        };
    }

    private ComponentEventListener<ClickEvent<Button>> openEditor() {
        return (ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
            GeneratorFormData generatorFormData = GeneratorFormData.builder()
                .aboutTheJobValue(documentContent.getJobDescription())
                .companyBenefitsValue(getStringFromList.apply(documentContent.getBenefits()))
                .companyDescriptionValue(documentContent.getCompanyDescription())
                .footerFieldValue(documentContent.getCustomEnd())
                .headerFieldValue(documentContent.getHeader())
                .requirementsValue(getStringFromList.apply(documentContent.getRequirements()))
                .responsibilitiesValue(getStringFromList.apply(documentContent.getResponsibilities()))
                .techStackValue(getStringFromList.apply(documentContent.getTechStack()))
                .build();

            generatorForm.setFormContent(generatorFormData);
            dialog.open();
        };
    }

    private ComponentEventListener<ClickEvent<Button>> generateAndDisplayPdf() {
        return (ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
            final File temporaryPdfFile = getPdfContent();

            temporaryFiles.add(temporaryPdfFile);
            changeBody.setEnabled(true);
            generate.setEnabled(false);
            generateAndDisplayPdf(temporaryPdfFile);
        };
    }

    private ComponentEventListener<OpenedChangeEvent<Dialog>> onDialogClose() {
        return (ComponentEventListener<OpenedChangeEvent<Dialog>>) onDialogClose -> {
            if (isDialogOpen) {
                documentContent = generatorForm.getDocumentContent();
                File temporaryPdfFile = getPdfContent();

                isDialogOpen = false;
                temporaryFiles.add(temporaryPdfFile);
                generateAndDisplayPdf(temporaryPdfFile);
            }
        };
    }

    private void buildDocumentData() {
        documentContent = new DocumentContent(department, grade);
    }

    private void generateAndDisplayPdf(File temporaryPdfFile) {
        final PdfPreviewer pdfToDisplay = PdfPreviewer.getInstance(temporaryPdfFile);
        previewLayout.replace(previewLayout.getComponentAt(0), pdfToDisplay);
    }

    private File getPdfContent() {
        return new PdfFileGenerator()
            .setHeader(documentContent.getHeader())
            .setCompanyData(documentContent.getCompanyDescription())
            .setAboutJob(documentContent.getJobDescription())
            .setRequirements(documentContent.getRequirements())
            .setTechStack(documentContent.getTechStack())
            .setResponsibilities(documentContent.getResponsibilities())
            .setCompanyBenefits(documentContent.getBenefits())
            .setFooter(documentContent.getCustomEnd())
            .getTemporaryPdfFile();
    }

    @Override
    public void beforeLeave(BeforeLeaveEvent beforeLeaveEvent) {
        for (File file : temporaryFiles) {
            final boolean delete = file.delete();
            if (!delete) {
                logger.error("Cant' remove temporary file!");
            }
        }
    }
}