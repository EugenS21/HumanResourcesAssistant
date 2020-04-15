package com.humanresources.assistant.ui.mainpage;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class MainPage extends VerticalLayout {

    H1 welcome;
    Span content;
    TileGrid tileGrid;

    public MainPage() {
        setAlignItems(Alignment.CENTER);
        welcome = new H1("Welcome to our site");
        content = new Span(
            "This site will guide you with your human resources relations between your employees and HR department."
                + "Check out our four main modules:");

        tileGrid = new TileGrid();
        tileGrid.setWidthFull();
        tileGrid.getStyle().set("overflow", "hidden");
        add(welcome, content, tileGrid);
    }
}
