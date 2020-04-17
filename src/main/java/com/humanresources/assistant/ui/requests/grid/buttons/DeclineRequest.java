package com.humanresources.assistant.ui.requests.grid.buttons;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.notification.Notification;

public class DeclineRequest extends Button {

    public DeclineRequest() {
        setText("Decline");
        setWidthFull();
        addThemeVariants(ButtonVariant.MATERIAL_OUTLINED);
        addClickListener(onClick());
    }

    // TODO implement action on button click
    private ComponentEventListener<ClickEvent<Button>> onClick() {
        return event -> Notification.show("Decline was hit!");
    }
}
