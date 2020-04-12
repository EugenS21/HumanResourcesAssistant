package com.humanresources.assistant.ui.bonuses.modal.tabs;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.Getter;

@Getter
public class ModalBody extends VerticalLayout {

    private final Component body;

    public ModalBody(Component body) {
        this.body = body;
        getStyle().set("overflow", "auto");
        addClassNames("hide-scroll", "card-list-view");
        add(body);
    }
}
