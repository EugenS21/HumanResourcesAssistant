package com.humanresources.assistant.ui.requests.grid.modal;

import static java.lang.String.format;

import com.humanresources.assistant.backend.dto.LeaveDto;
import com.humanresources.assistant.ui.requests.grid.buttons.ApproveRequest;
import com.humanresources.assistant.ui.requests.grid.buttons.DeclineRequest;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class LeaveModal extends Dialog {

    private final VerticalLayout body;
    private final HorizontalLayout buttons;
    private final ApproveRequest approve;
    private final DeclineRequest decline;
    private final Span content;

    public LeaveModal(LeaveDto selectedItem) {

        ComponentUtil.setData(UI.getCurrent(), LeaveDto.class, selectedItem);
        ComponentUtil.setData(UI.getCurrent(), LeaveModal.class, this);
        body = new VerticalLayout();
        buttons = new HorizontalLayout();
        final String dialogBody = format(
            "Please review %s selectedItem for %s %s which will start on %s, %s due to %s",
            selectedItem.getLeaveType().toString().toLowerCase().replaceAll("_", " "),
            selectedItem.getName(),
            selectedItem.getSurName(),
            selectedItem.getStartDate().getDayOfWeek().toString(),
            selectedItem.getStartDate(),
            selectedItem.getEndDate());
        content = new Span(dialogBody);
        approve = new ApproveRequest();
        decline = new DeclineRequest();

        buttons.add(approve, decline);
        buttons.setAlignItems(Alignment.STRETCH);
        buttons.setPadding(true);
        body.add(content);
        add(body, buttons);
    }
}
