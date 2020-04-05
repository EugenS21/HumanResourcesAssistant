package com.humanresources.assistant.ui.bonuses;

import com.humanresources.assistant.ui.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "salary_rise",
       layout = MainLayout.class)
@PageTitle("Salary Rise Management")
public class SalaryRise extends VerticalLayout {

    public static final String VIEW_NAME = "Salary Rise";

}
