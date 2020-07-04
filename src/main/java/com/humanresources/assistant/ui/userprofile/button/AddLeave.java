package com.humanresources.assistant.ui.userprofile.button;

import com.humanresources.assistant.ui.userprofile.modals.UserLeaveRequestModal;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;

public class AddLeave extends Button {

    public AddLeave() {
        setText("Add Leave");
        setAutofocus(true);
        addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        setSizeFull();
        addClickListener(onClickOpenModal());
    }

    private ComponentEventListener<ClickEvent<Button>> onClickOpenModal() {
        return (ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
            final UserLeaveRequestModal userLeaveRequestModal = new UserLeaveRequestModal();
            userLeaveRequestModal.open();
        };
    }
}
