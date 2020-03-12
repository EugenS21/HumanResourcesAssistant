package com.humanresources.assistant.ui.cvs;

import com.humanresources.assistant.ui.MainLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "CVs", layout = MainLayout.class)
@PageTitle("Curriculum Vitae To Analyze")
public class CVs extends HorizontalLayout {

    public static final String VIEW_NAME = "CVs list";

    public CVs() {
        add(VaadinIcon.DIPLOMA.create());
        add(new Span(" Here should be a list of CVs which were sent by people "));
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
    }


}
