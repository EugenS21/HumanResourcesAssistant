package com.humanresources.assistant.ui.cvs;

import com.humanresources.assistant.backend.model.Department;
import com.humanresources.assistant.backend.model.Grade;
import com.humanresources.assistant.ui.MainLayout;
import com.humanresources.assistant.ui.pdf.EmbeddedPdfDocument;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@Route(value = "Generator",
       layout = MainLayout.class)
@PageTitle("CV generator")
public class Generator extends HorizontalLayout {

    public static final String VIEW_NAME = "CVs Generator";
    private final TextField textField;
    private final Select<Department> departmentSelect;
    private final Select<Grade> grade;
    private final Button generate;
    private final String fileName = "src/main/resources/Java_8_in_Action.pdf";
    private final VerticalLayout optionsLayout;
    private final VerticalLayout previewLayout;

    public Generator() {
        optionsLayout = new VerticalLayout();
        previewLayout = new VerticalLayout();
        optionsLayout.setAlignItems(Alignment.CENTER);
        previewLayout.setAlignItems(Alignment.CENTER);
        setSizeFull();
        textField = new TextField();
        departmentSelect = new Select<>();
        generate = new Button();
        generate.setText("Generate CV");
        departmentSelect.setItems(Department.values());
        departmentSelect.setLabel("Department");
        grade = new Select<>();
        grade.setLabel("Grade");
        grade.setItems(Grade.values());
        final EmbeddedPdfDocument embeddedPdfDocument = new EmbeddedPdfDocument(new StreamResource("Java_8_in_Action.pdf", () -> {
            try {
                return new FileInputStream("src/main/resources/Java_8_in_Action.pdf");
            } catch (FileNotFoundException e) {
                return new ByteArrayInputStream(new byte[]{});
            }
        }));
        previewLayout.add(embeddedPdfDocument);
        optionsLayout.add(textField, departmentSelect, grade, generate);
        add(optionsLayout, previewLayout);
    }

    private InputStream getPdfInputStream() throws FileNotFoundException {
        return new FileInputStream(fileName);
    }
}