package com.humanresources.assistant.ui.cvs;

import com.humanresources.assistant.backend.enums.Department;
import com.humanresources.assistant.backend.enums.Grade;
import com.humanresources.assistant.backend.tools.pdf.PdfFileGenerator;
import com.humanresources.assistant.backend.tools.pdf.PdfPreviewer;
import com.humanresources.assistant.ui.MainLayout;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.details.DetailsVariant;
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

@Route(value = "Generator",
       layout = MainLayout.class)
@PageTitle("CV generator")
public class Generator extends HorizontalLayout implements BeforeLeaveObserver {

    private Logger logger = LoggerFactory.getLogger(Generator.class);
    public static final String VIEW_NAME = "CVs Generator";

    private final Select<Department> departmentSelect;
    private final Select<Grade> grade;
    private final Button generate;
    private final Button changeBody;
    private final Details details;
    private final VerticalLayout optionsLayout;
    private final VerticalLayout previewLayout;
    private List<File> temporaryFiles;
    private PdfPreviewer pdfPreviewer;

    public Generator() {
        setSizeFull();
        temporaryFiles = new ArrayList<>();
        optionsLayout = new VerticalLayout();
        previewLayout = new VerticalLayout();
        generate = new Button();
        departmentSelect = new Select<>();
        grade = new Select<>();
        details = new Details();
        pdfPreviewer = new PdfPreviewer();
        changeBody = new Button();

        initializeFrontEndComponents();

        previewLayout.add(pdfPreviewer);
        optionsLayout.add(details, departmentSelect, grade, generate);
        add(optionsLayout, previewLayout);
    }

    private void initializeFrontEndComponents() {
        optionsLayout.setAlignItems(Alignment.CENTER);
        previewLayout.setAlignItems(Alignment.CENTER);
        generate.setText("Generate CV");
        departmentSelect.setItems(Department.values());
        departmentSelect.setLabel("Department");
        grade.setLabel("Grade");
        grade.setItems(Grade.values());
        details.setSummaryText("Welcome to our CVs generator");
        details.addThemeVariants(DetailsVariant.REVERSE, DetailsVariant.FILLED);
        details.addContent(new ListItem("Select option from the existing selections;"));
        details.addContent(new ListItem("Please on the generate button;"));
        details.addContent(new ListItem("Wait for the CV to be prepared and check the right side;"));
        details.addContent(new ListItem("Share the pdf document to all the available services."));
        generate.addClickListener(generateAndDisplayPdf());
    }

    private ComponentEventListener<ClickEvent<Button>> generateAndDisplayPdf() {
        return (ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
            final File temporaryPdfFile = new PdfFileGenerator()
                .setHeader(getHeader())
                .setBody(getBody())
                .getTemporaryPdfFile();
            temporaryFiles.add(temporaryPdfFile);

            final PdfPreviewer pdfToDisplay = PdfPreviewer.getInstance(temporaryPdfFile);
            previewLayout.replace(pdfPreviewer, pdfToDisplay);
        };
    }

    private String getHeader() {
        return "AUTOMATION ENGINEER";
    }

    private String getBody() {
        return
            "Pentalog is a digital leader whose platform offers a digital one-stop shop of services that disrupts traditional\n"
                + "value chains. The company is operating internationally in Romania, France, Moldova, Vietnam, USA and Germany"
                + ".\n"
                + "The company employs over 1500 engineers and IT experts who work in a very dynamic, multicultural working "
                + "environment,\n"
                + "where your talents and ambitions are recognized and rewarded and where you will have plenty of opportunities"
                + " to develop\n"
                + "as an individual and as a professional.";
    }

    private String aboutTheJob() {
        return null;
    }

    private String getRequirements() {
        return null;
    }

    private String getResponsibilities() {
        return null;
    }

    private String getBenefits() {
        return null;
    }

    private String getCustomEnd() {
        return null;
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