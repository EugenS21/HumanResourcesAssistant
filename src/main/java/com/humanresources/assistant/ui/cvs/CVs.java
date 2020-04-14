package com.humanresources.assistant.ui.cvs;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

import com.github.javafaker.Faker;
import com.humanresources.assistant.backend.enums.Department;
import com.humanresources.assistant.backend.model.uimodels.CurriculumVitae;
import com.humanresources.assistant.backend.tools.pdf.PdfPreviewer;
import com.humanresources.assistant.ui.MainLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.selection.SelectionListener;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

@Route(value = "cvs_list", layout = MainLayout.class)
@PageTitle("Curriculum Vitae List")
@CssImport(value = "styles/views/cardlist/card-list-view.css", include = "lumo-badge")
@JsModule("@vaadin/vaadin-lumo-styles/badge.js")
public class CVs extends VerticalLayout implements AfterNavigationObserver {

    public static final String VIEW_NAME = "CVs list";

    private final Grid<CurriculumVitae> cvsGrid;
    private final Dialog dialog;
    private PdfPreviewer pdfPreviewer;

    public CVs() {
        cvsGrid = new Grid<>();
        dialog = new Dialog();

        setId("card-list-view");
        addClassName("card-list-view");
        setSizeFull();

        initializeDialog();
        initializeGrid();

        add(cvsGrid, dialog);

    }

    private HorizontalLayout createCard(CurriculumVitae curriculumVitae) {
        HorizontalLayout card = new HorizontalLayout();
        card.addClassName("card");
        card.setSpacing(false);

        Image pdfImage = new Image();
        pdfImage.setClassName("img");
        pdfImage.setSrc("http://icons.iconarchive.com/icons/treetog/file-type/256/pdf-icon.png");

        VerticalLayout cardBody = new VerticalLayout();
        cardBody.addClassName("description");
        cardBody.setPadding(false);
        cardBody.setSpacing(false);

        HorizontalLayout header = new HorizontalLayout();
        header.addClassName("header");
        header.setSpacing(false);
        Span applicantName = new Span(curriculumVitae.getUserDetails());
        applicantName.setClassName("name");
        Span position = new Span(curriculumVitae.getJobToApply().getName());
        position.addClassName("date");
        header.add(applicantName, position);

        Span applicantDescription = new Span(curriculumVitae.getDescription());
        applicantDescription.addClassName("post");

        Span applicantPhone = new Span(curriculumVitae.getUserPhoneNumber());
        applicantPhone.addClassName("comments");
        Anchor cvLinkToDownload = new Anchor();
        cvLinkToDownload.setText("Download");
        cvLinkToDownload.addClassName("comments");

        HorizontalLayout jobData = new HorizontalLayout();
        jobData.add(applicantPhone, cvLinkToDownload);

        cardBody.add(header, applicantDescription, jobData);
        card.add(pdfImage, cardBody);
        return card;
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        List<CurriculumVitae> cvs = new ArrayList<>();
        final Locale locale = new Locale("en", "US");
        final Faker fakerData = new Faker(locale);
        for (int i = 0; i < 200; i++) {
//            cvs.add(generateCv());

            cvs.add(CurriculumVitae.builder()
                        .userDetails(fakerData.name().fullName())
                        .userPhoneNumber(fakerData.phoneNumber().phoneNumber())
                        .description(fakerData.lorem().sentence(150))
                        .jobToApply(Department.values()[new Random().nextInt(Department.values().length - 1)])
                        .build());
        }
        cvsGrid.setItems(cvs);
    }

    private static String getRandom(int count) {
        return randomAlphanumeric(count);
    }

    private static CurriculumVitae generateCv() {
        final Locale locale = new Locale("en", "US");
        final Faker fakerData = new Faker(locale);
        return CurriculumVitae.builder()
            .userDetails(fakerData.name().fullName())
            .userPhoneNumber(fakerData.phoneNumber().phoneNumber())
            .description("fakerData.lorem().sentence(150)")
            .jobToApply(Department.values()[new Random().nextInt(Department.values().length - 1)])
            .build();
    }

    private void initializeDialog() {
        dialog.setWidth("800px");
        dialog.setHeight("900px");
    }

    private void initializeGrid() {
        cvsGrid.setHeight("100%");
        cvsGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS);
        cvsGrid.addComponentColumn(this::createCard);
        cvsGrid.addSelectionListener(selectionListener());
        File file = new File("src/main/resources/Java_8_in_Action.pdf");
        pdfPreviewer = PdfPreviewer.getInstance(file);
        dialog.add(pdfPreviewer);
    }

    private SelectionListener<Grid<CurriculumVitae>, CurriculumVitae> selectionListener() {
        return event -> dialog.open();
    }

}
