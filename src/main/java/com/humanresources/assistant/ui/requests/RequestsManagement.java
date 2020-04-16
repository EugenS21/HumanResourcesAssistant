package com.humanresources.assistant.ui.requests;

import com.humanresources.assistant.ui.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "requests_management", layout = MainLayout.class)
@PageTitle("Requests Management")
public class RequestsManagement extends VerticalLayout {

    public static final String VIEW_NAME = "Requests";

    public RequestsManagement() {

    }
}
