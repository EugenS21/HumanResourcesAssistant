package com.humanresources.assistant.ui.bonuses;

import static java.time.LocalDateTime.now;

import com.github.javafaker.Faker;
import com.humanresources.assistant.backend.enums.Department;
import com.humanresources.assistant.backend.enums.Grade;
import com.humanresources.assistant.backend.model.uimodels.bonuses.profile.FeedbackInformationFieldValues;
import com.humanresources.assistant.ui.MainLayout;
import com.humanresources.assistant.ui.bonuses.profile.BonusProfile;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.selection.SelectionListener;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Route(value = "grade_rise",
       layout = MainLayout.class)
@PageTitle("Grade Rise Management")
@CssImport(value = "styles/views/cardlist/card-list-view.css",
           include = "lumo-badge")
@JsModule("@vaadin/vaadin-lumo-styles/badge.js")
public class GradeRise extends VerticalLayout implements AfterNavigationObserver {

    public static final String VIEW_NAME = "Grade Rise";
    private final Grid<FeedbackInformationFieldValues> feedbackGrid;
    private final Button button;
    private final Dialog dialog;
    private final BonusProfile bonusProfile;

    public GradeRise() {
        feedbackGrid = new Grid<>();
        dialog = new Dialog();
        button = new Button();
        button.setText("PLM OPEN THIS");
        bonusProfile = new BonusProfile();

        setId("card-list-view");
        addClassName("card-list-view");
        setSizeFull();
        initializeGrid();

        dialog.setSizeFull();
        add(feedbackGrid, dialog, button);
        button.addClickListener(addListener());
    }

    private void initializeGrid() {
        feedbackGrid.addThemeVariants(GridVariant.LUMO_COMPACT, GridVariant.LUMO_COMPACT);
        feedbackGrid.addComponentColumn(this::createCard);
    }

    private HorizontalLayout createCard(FeedbackInformationFieldValues feedbackValues) {
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
        String name = feedbackValues.getFirstName() + " " + feedbackValues.getLastName();
        Span feedbackPerson = new Span(name);
        feedbackPerson.setClassName("name");
        String personGrade = feedbackValues.getGrade().toString() + " " + feedbackValues.getDepartment().toString();
        Span personPosition = new Span(personGrade);
        personPosition.addClassName("date");
        header.add(feedbackPerson, personPosition);

        Span applicantDescription = new Span(feedbackValues.getDescription());
        applicantDescription.addClassName("post");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String dateAndProject = feedbackValues.getLocalDateTime().format(formatter) + " " + feedbackValues.getProject();
        Span applicantPhone = new Span(dateAndProject);
        applicantPhone.addClassName("comments");

        HorizontalLayout jobData = new HorizontalLayout();
        jobData.add(applicantPhone);

        cardBody.add(header, applicantDescription, jobData);
        card.add(pdfImage, cardBody);
        return card;
    }


    private ComponentEventListener<ClickEvent<Button>> addListener() {
        return event -> {
            dialog.add(bonusProfile);
            dialog.open();
        };
    }

    private SelectionListener<Grid<BonusProfile>, BonusProfile> selectionListener() {
        return event -> dialog.open();
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        List<FeedbackInformationFieldValues> persons = new ArrayList<>();
        Locale locale = new Locale("en", "US");
        final Faker faker = new Faker(locale);
        for (int i = 0; i < 30; i++) {
            final List<Department> departments = Arrays.stream(Department.values()).collect(Collectors.toList());
            final List<Grade> grades = Arrays.stream(Grade.values()).collect(Collectors.toList());
            Collections.shuffle(departments);
            Collections.shuffle(grades);
            final Department department = departments.get(2);
            final Grade grade = grades.get(1);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            persons.add(FeedbackInformationFieldValues.builder()
                            .department(department)
                            .description(faker.lorem().sentence(15))
                            .firstName(faker.name().firstName())
                            .lastName(faker.name().lastName())
                            .grade(grade)
                            .localDateTime(now())
                            .project(faker.gameOfThrones().house())
                            .build());
        }
        feedbackGrid.setItems(persons);
    }
}
