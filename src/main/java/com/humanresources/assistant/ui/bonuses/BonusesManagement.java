package com.humanresources.assistant.ui.bonuses;

import static java.time.LocalDateTime.now;

import com.github.javafaker.Faker;
import com.humanresources.assistant.backend.enums.Department;
import com.humanresources.assistant.backend.enums.Grade;
import com.humanresources.assistant.backend.model.uimodels.bonuses.profile.BonusFields;
import com.humanresources.assistant.ui.MainLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.BeforeLeaveEvent;
import com.vaadin.flow.router.BeforeLeaveObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Collectors;

@Route(value = "bonuses_management", layout = MainLayout.class)
@PageTitle("Grade Rise Management")
@CssImport(value = "styles/views/cardlist/card-list-view.css", include = "lumo-badge")
@JsModule("@vaadin/vaadin-lumo-styles/badge.js")
public class BonusesManagement extends VerticalLayout implements AfterNavigationObserver, BeforeLeaveObserver {

    public static final String VIEW_NAME = "Bonuses";

    private BonusesRequestsGrid bonusesGrid;

    public BonusesManagement() {
        setHeightFull();
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        List<BonusFields> persons = new ArrayList<>();
        Locale locale = new Locale("en", "US");
        final Faker faker = new Faker(locale);
        for (int i = 0; i < 100; i++) {
            final List<Department> departments = Arrays.stream(Department.values()).collect(Collectors.toList());
            final List<Grade> grades = Arrays.stream(Grade.values()).collect(Collectors.toList());
            Collections.shuffle(departments);
            Collections.shuffle(grades);
            final Department department = departments.get(2);
            final Grade grade = grades.get(1);
            persons.add(
                BonusFields.builder()
                    .department(department)
                    .motivation(faker.lorem().sentence(15))
                    .firstName(faker.name().firstName())
                    .lastName(faker.name().lastName())
                    .grade(grade)
                    .localDateTime(now().plusDays(new Random().nextInt(366)))
                    .project(faker.gameOfThrones().house())
                    .build());
        }
        bonusesGrid = new BonusesRequestsGrid(persons);
        add(bonusesGrid);
    }

    @Override
    public void beforeLeave(BeforeLeaveEvent beforeLeaveEvent) {
        removeAll();
    }
}
