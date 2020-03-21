package com.humanresources.assistant.ui.cvs;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

import com.humanresources.assistant.backend.model.CurriculumVitae;
import com.humanresources.assistant.backend.model.Department;
import com.humanresources.assistant.ui.MainLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Route(value = "CVs",
       layout = MainLayout.class)
@PageTitle("Curriculum Vitae List")
@CssImport(value = "styles/views/cardlist/card-list-view.css",
           include = "lumo-badge")
@JsModule("@vaadin/vaadin-lumo-styles/badge.js")
public class CVs extends Div implements AfterNavigationObserver {

    public static final String VIEW_NAME = "CVs list";

    Grid<CurriculumVitae> cvsGrid = new Grid<>();

    public CVs() {
        setId("card-list-view");
        addClassName("card-list-view");
        setSizeFull();
        cvsGrid.setHeight("100%");
        cvsGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS);
        cvsGrid.addComponentColumn(this::createCard);
        add(cvsGrid);
    }

    private HorizontalLayout createCard(CurriculumVitae curriculumVitae) {
        HorizontalLayout card = new HorizontalLayout();
        card.addClassName("card");
        card.setSpacing(false);

        Image pdfImage = new Image();
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
        for (int i = 0; i < 10; i++) {
            cvs.add(generateCv());
        }
        cvsGrid.setItems(cvs);
    }

    private static String getRandom(int count) {
        return randomAlphanumeric(count);
    }

    private static CurriculumVitae generateCv() {
        return CurriculumVitae.builder()
            .userDetails(getRandom(10))
            .userPhoneNumber(getRandom(8))
//            .description(getRandom(100))
            .description("Here should be a nice super, nice description, yeap boy")
            .jobToApply(Department.values()[new Random().nextInt(Department.values().length - 1)])
            .build();
    }


}
