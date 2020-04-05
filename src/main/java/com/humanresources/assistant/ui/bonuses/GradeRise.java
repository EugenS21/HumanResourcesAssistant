package com.humanresources.assistant.ui.bonuses;

import com.humanresources.assistant.ui.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "grade_rise",
       layout = MainLayout.class)
@PageTitle("Grade Rise Management")
public class GradeRise extends VerticalLayout {

    public static final String VIEW_NAME = "Grade Rise";
}
