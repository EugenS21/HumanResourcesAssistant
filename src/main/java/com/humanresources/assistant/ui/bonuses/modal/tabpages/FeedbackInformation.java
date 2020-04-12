package com.humanresources.assistant.ui.bonuses.modal.tabpages;

import static java.time.format.DateTimeFormatter.ofPattern;

import com.github.appreciated.card.Card;
import com.github.appreciated.card.label.TitleLabel;
import com.github.javafaker.Faker;
import com.humanresources.assistant.backend.enums.Department;
import com.humanresources.assistant.backend.enums.Grade;
import com.humanresources.assistant.backend.model.uimodels.bonuses.profile.FeedbackFields;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Collectors;

@CssImport(value = "styles/views/cardlist/card-list-view.css")
@JsModule("@vaadin/vaadin-lumo-styles/badge.js")
public class FeedbackInformation extends VerticalLayout {

    private final static String DATE_PATTERN = "dd/MM/yyyy HH:mm";

    private final List<Card> feedbackCards;

    public FeedbackInformation() {
        feedbackCards = new ArrayList<>();

        initializeCards();

        feedbackCards.forEach(this::add);
    }

    private Card initializeCard(FeedbackFields informationFieldValues) {
        final Card card = new Card();
        final HorizontalLayout header = new HorizontalLayout();
        final HorizontalLayout description = new HorizontalLayout();
        final HorizontalLayout footer = new HorizontalLayout();
        card.setSizeFull();

        Image pdfImage = new Image();
        pdfImage.setSrc("http://icons.iconarchive.com/icons/treetog/file-type/256/pdf-icon.png");
        pdfImage.setClassName("customimg");
        TitleLabel titleLabel = new TitleLabel(informationFieldValues.getFullName()).withWhiteSpaceNoWrap();
        header.add(pdfImage, titleLabel);

        Paragraph descriptionText = new Paragraph(informationFieldValues.getFeedBack());
        descriptionText.addClassName("second-text");
        description.add(descriptionText);

        Span jobData = new Span(informationFieldValues.getFullJobData());
        jobData.addClassName("footer");
        DateTimeFormatter dateTimeFormatter = ofPattern(DATE_PATTERN);
        Span dateAndTime = new Span(informationFieldValues.getLocalDateTime().format(dateTimeFormatter));
        dateAndTime.addClassName("footer");
        Span project = new Span(informationFieldValues.getProject());
        project.addClassName("footer");
        footer.add(dateAndTime, jobData, project);

        card.add(header, description, footer);
        return card;
    }

    private void initializeCards() {
        Locale locale = new Locale("en", "US");
        Faker faker = new Faker(locale);
        for (int i = 0; i < 10; i++) {
            final List<Grade> streamGrade = Arrays.stream(Grade.values()).collect(Collectors.toList());
            Collections.shuffle(streamGrade);
            final Grade grade = streamGrade.get(0);
            final List<Department> streamDepartment = Arrays.stream(Department.values()).collect(Collectors.toList());
            Collections.shuffle(streamDepartment);
            final Department department = streamDepartment.get(0);
            final Random random = new Random();
            feedbackCards.add(
                initializeCard(
                    FeedbackFields.builder()
                        .firstName(faker.name().firstName())
                        .lastName(faker.name().lastName())
                        .feedBack(faker.lorem().sentence(random.nextInt(50)))
                        .localDateTime(LocalDateTime.now().plusDays(random.nextInt(366)))
                        .project(faker.gameOfThrones().house())
                        .grade(grade)
                        .department(department)
                        .build()));
        }
    }
}
