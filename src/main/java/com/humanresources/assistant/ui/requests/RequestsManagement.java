package com.humanresources.assistant.ui.requests;

import com.github.javafaker.Faker;
import com.humanresources.assistant.backend.enums.RequestType;
import com.humanresources.assistant.backend.model.Request;
import com.humanresources.assistant.ui.MainLayout;
import com.humanresources.assistant.ui.requests.grid.RequestsGrid;
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

@Route(value = "requests_management", layout = MainLayout.class)
@PageTitle("Requests Management")
public class RequestsManagement extends VerticalLayout implements AfterNavigationObserver, BeforeLeaveObserver {

    public static final String VIEW_NAME = "Requests";
    private RequestsGrid requestsGrid;

    public RequestsManagement() {
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
                    .requestType(RequestType.values()[new Random().nextInt(RequestType.values().length - 1)])
                    .build()
            );
        }

        requestsGrid = new RequestsGrid(requests);
        add(requestsGrid);
    }

    @Override
    public void beforeLeave(BeforeLeaveEvent beforeLeaveEvent) {
        remove(requestsGrid);
    }
}
