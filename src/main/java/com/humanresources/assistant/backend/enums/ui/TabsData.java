package com.humanresources.assistant.backend.enums.ui;

import static com.vaadin.flow.component.icon.VaadinIcon.ARCHIVE;
import static com.vaadin.flow.component.icon.VaadinIcon.INFO;
import static com.vaadin.flow.component.icon.VaadinIcon.NOTEBOOK;

import com.vaadin.flow.component.icon.VaadinIcon;
import lombok.Getter;

public enum TabsData {

    GENERAL("General Information", INFO),
    FEEDBACK("Employee Feedback", NOTEBOOK),
    HISTORY("Bonuses History", ARCHIVE);

    @Getter
    private final String name;
    @Getter
    private final VaadinIcon icon;

    TabsData(String name, VaadinIcon icon) {
        this.name = name;
        this.icon = icon;
    }
}
