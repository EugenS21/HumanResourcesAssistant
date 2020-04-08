package com.humanresources.assistant.ui.bonuses.profile;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class HistoryInformation extends VerticalLayout {

    private final TextField textField;

    public HistoryInformation() {
        setSizeFull();
        textField = new TextField();
        textField.setTitle("Ebana");
        textField.setPlaceholder("PLM");
        textField.setVisible(true);
        add(textField);
    }
}
