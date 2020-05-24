package com.humanresources.assistant.ui.requests;

import com.github.javafaker.Faker;
import com.humanresources.assistant.backend.enums.LeaveTypeEnum;
import com.humanresources.assistant.backend.model.Request;
import com.humanresources.assistant.ui.MainLayout;
import com.humanresources.assistant.ui.requests.grid.LeavesGrid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.BeforeLeaveEvent;
import com.vaadin.flow.router.BeforeLeaveObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

@Route (value = "leaves_management", layout = MainLayout.class)
@PageTitle ("Leaves Management")
public class LeavesManagement extends VerticalLayout implements AfterNavigationObserver, BeforeLeaveObserver {

    public static final String VIEW_NAME = "Leaves";
    private LeavesGrid requestsGrid;

    public LeavesManagement() {
        setSizeFull();
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        final List<Request> requests = new ArrayList<>();
        Locale locale = new Locale("en", "US");
        final Faker faker = new Faker(locale);
        for (int i = 0; i < 1020; i++) {
            final LocalDate parse = LocalDate.now().minusDays(new Random().nextInt(300));
            requests.add(
                Request.builder()
                    .firstName(faker.name().firstName())
                    .lastName(faker.name().lastName())
                    .firstDate(parse)
                    .lastDate(parse.plusDays(10))
                    .requestType(LeaveTypeEnum.values()[new Random().nextInt(LeaveTypeEnum.values().length - 1)])
                    .build()
            );
        }

        requestsGrid = new LeavesGrid(requests);
        add(requestsGrid);
    }

    @Override
    public void beforeLeave(BeforeLeaveEvent beforeLeaveEvent) {
        remove(requestsGrid);
    }
}
