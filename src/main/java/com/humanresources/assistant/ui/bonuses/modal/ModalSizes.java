package com.humanresources.assistant.ui.bonuses.modal;

import lombok.Getter;

public enum ModalSizes {
    HEIGHT("820px"),
    WIDTH("660px");

    @Getter
    private final String size;

    ModalSizes(String size) {
        this.size = size;
    }
}
