package com.humanresources.assistant.ui.requests.grid.modal;

import static java.lang.String.format;

import com.humanresources.assistant.backend.model.Request;
import com.humanresources.assistant.ui.requests.grid.buttons.ApproveRequest;
import com.humanresources.assistant.ui.requests.grid.buttons.DeclineRequest;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class RequestModal extends Dialog {

    private final VerticalLayout body;
    private final HorizontalLayout buttons;
    private final ApproveRequest approve;
    private final DeclineRequest decline;
    private final Span content;

    public RequestModal(Request request) {

        body = new VerticalLayout();
        buttons = new HorizontalLayout();

        final String dialogBody = format("Please review %s request for %s %s which will start on %s, %s due to %s",
                                         request.getRequestType().toString().toLowerCase().replaceAll("_", " "),
                                         request.getFirstName(),
                                         request.getLastName(),
                                         request.getFirstDate().getDayOfWeek().toString(),
                                         request.getFirstDate(),
                                         request.getLastDate());
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
