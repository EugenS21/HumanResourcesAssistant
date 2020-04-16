package com.humanresources.assistant.ui.mainpage;

import com.humanresources.assistant.ui.mainpage.tiles.BonusesManagement;
import com.humanresources.assistant.ui.mainpage.tiles.CVsManagement;
import com.humanresources.assistant.ui.mainpage.tiles.DocumentsManagement;
import com.humanresources.assistant.ui.mainpage.tiles.EmployeeManagement;
import com.humanresources.assistant.ui.mainpage.tiles.JobGenerator;
import com.humanresources.assistant.ui.mainpage.tiles.RequestsManagement;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class TileGrid extends Div {

    private final HorizontalLayout firstLayout;
    private final Div divider;
    private final HorizontalLayout secondLayout;
    private final EmployeeManagement employeeManagement;
    private final DocumentsManagement documentsManagement;
    private final BonusesManagement bonusesManagement;
    private final RequestsManagement requestsManagement;
    private final CVsManagement cVsManagement;
    private final JobGenerator jobGenerator;

    public TileGrid() {
        firstLayout = new HorizontalLayout();
        divider = new Div();
        secondLayout = new HorizontalLayout();
        employeeManagement = new EmployeeManagement();
        documentsManagement = new DocumentsManagement();
        bonusesManagement = new BonusesManagement();
        requestsManagement = new RequestsManagement();
        cVsManagement = new CVsManagement();
        jobGenerator = new JobGenerator();

        firstLayout.addAndExpand(employeeManagement, documentsManagement, cVsManagement);
        secondLayout.addAndExpand(jobGenerator, bonusesManagement, requestsManagement);

        add(firstLayout, divider, secondLayout);
    }
}
