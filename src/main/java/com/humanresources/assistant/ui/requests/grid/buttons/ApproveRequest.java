package com.humanresources.assistant.ui.requests.grid.buttons;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.notification.Notification;

public class ApproveRequest extends Button {

    public ApproveRequest() {
        setText("Approve");
        setWidthFull();
        addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addClickListener(onClick());
    }

    // TODO implement action on button click
    private ComponentEventListener<ClickEvent<Button>> onClick() {
        return event -> Notification.show("Approve was hit!");
    }
}
