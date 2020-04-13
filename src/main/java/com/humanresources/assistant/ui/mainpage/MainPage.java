package com.humanresources.assistant.ui.mainpage;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class MainPage extends VerticalLayout {

    H2 welcome;
    Span content;

    public MainPage() {
        setAlignItems(Alignment.CENTER);
        welcome = new H2("Welcome to our site");
        content = new Span(
            "This site will guide you with your human resources relations between your employees and HR department."
                + "Check out our four main modules:");
        add(welcome, content);
    }
}
