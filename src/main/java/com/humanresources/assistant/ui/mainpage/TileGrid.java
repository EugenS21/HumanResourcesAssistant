package com.humanresources.assistant.ui.mainpage;

import static com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment.CENTER;

import com.humanresources.assistant.ui.mainpage.tiles.CVsGenerator;
import com.humanresources.assistant.ui.mainpage.tiles.CVsManagement;
import com.humanresources.assistant.ui.mainpage.tiles.DocumentsManagement;
import com.humanresources.assistant.ui.mainpage.tiles.EmployeeManagement;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

@CssImport(value = "styles/views/divider.css")
public class TileGrid extends Div {

    private final HorizontalLayout firstLayout;
    private final HorizontalLayout secondLayout;
    private final Div divider;
    private final Span pre;
    private final Span text;
    private final Span post;
    private final EmployeeManagement employeeManagement;
    private final DocumentsManagement documentsManagement;
    private final CVsManagement cVsManagement;
    private final CVsGenerator cVsGenerator;

    public TileGrid() {
        firstLayout = new HorizontalLayout();
        secondLayout = new HorizontalLayout();
        divider = new Div();
        pre = new Span();
        text = new Span("More modules");
        post = new Span();
        employeeManagement = new EmployeeManagement();
        documentsManagement = new DocumentsManagement();
        cVsManagement = new CVsManagement();
        cVsGenerator = new CVsGenerator();

        firstLayout.setAlignItems(CENTER);
        firstLayout.addAndExpand(employeeManagement, documentsManagement, cVsManagement);
        divider.add(pre, text, post);
        secondLayout.setAlignItems(CENTER);
        secondLayout.addAndExpand(cVsGenerator);

        add(firstLayout, divider, secondLayout);
    }
}
