package com.humanresources.assistant.ui.bonuses.profile;

import static java.time.LocalDateTime.parse;

import com.github.javafaker.Faker;
import com.humanresources.assistant.backend.enums.Department;
import com.humanresources.assistant.backend.enums.Grade;
import com.humanresources.assistant.backend.model.uimodels.bonuses.profile.FeedbackInformationFieldValues;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@CssImport(value = "styles/views/cardlist/card-list-view.css",
           include = "lumo-badge")
@JsModule("@vaadin/vaadin-lumo-styles/badge.js")
public class FeedbackInformation extends VerticalLayout implements AfterNavigationObserver {

    private final Grid<FeedbackInformationFieldValues> feedbackGrid;

    public FeedbackInformation() {
        feedbackGrid = new Grid<>();

        setId("card-list-view");
        addClassName("card-list-view");
        initializeGrid();

        add(feedbackGrid);
    }

    private HorizontalLayout createCard(FeedbackInformationFieldValues feedbackValues) {
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
        String name = feedbackValues.getFirstName() + " " + feedbackValues.getLastName();
        Span feedbackPerson = new Span(name);
        feedbackPerson.setClassName("name");
        String personGrade = feedbackValues.getGrade().toString() + " " + feedbackValues.getDepartment().toString();
        Span personPosition = new Span(personGrade);
        personPosition.addClassName("date");
        header.add(feedbackPerson, personPosition);

        Span applicantDescription = new Span(feedbackValues.getDescription());
        applicantDescription.addClassName("post");

        String dateAndProject = feedbackValues.getLocalDateTime() + " " + feedbackValues.getProject();
        Span applicantPhone = new Span(dateAndProject);
        applicantPhone.addClassName("comments");

        HorizontalLayout jobData = new HorizontalLayout();
        jobData.add(applicantPhone);

        cardBody.add(header, applicantDescription, jobData);
        card.add(pdfImage, cardBody);
        return card;
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        List<FeedbackInformationFieldValues> persons = new ArrayList<>();
        Locale locale = new Locale("en", "US");
        final Faker faker = new Faker(locale);
        for (int i = 0; i < 10; i++) {
            final List<Department> departments = Arrays.stream(Department.values()).collect(Collectors.toList());
            final List<Grade> grades = Arrays.stream(Grade.values()).collect(Collectors.toList());
            Collections.shuffle(departments);
            Collections.shuffle(grades);
            final Department department = departments.get(2);
            final Grade grade = grades.get(1);
            persons.add(FeedbackInformationFieldValues.builder()
                            .department(department)
                            .description(faker.lorem().sentence(10))
                            .firstName(faker.name().firstName())
                            .lastName(faker.name().lastName())
                            .grade(grade)
                            .localDateTime(parse(faker.date().toString()))
                            .project(faker.gameOfThrones().house())
                            .build());
        }
        feedbackGrid.setItems(persons);
    }

    private void initializeGrid() {
        feedbackGrid.addThemeVariants(GridVariant.LUMO_COMPACT, GridVariant.LUMO_COMPACT);
        feedbackGrid.addComponentColumn(this::createCard);
    }
}
