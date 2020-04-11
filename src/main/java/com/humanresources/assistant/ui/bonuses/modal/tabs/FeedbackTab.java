package com.humanresources.assistant.ui.bonuses.modal.tabs;

import static com.humanresources.assistant.backend.enums.ui.TabsData.FEEDBACK;

import com.humanresources.assistant.backend.enums.ui.TabsData;
import com.vaadin.flow.component.tabs.Tab;

public class FeedbackTab extends Tab implements ITabGenerics<Tab> {

    private final TabsData feedbackTabData = FEEDBACK;

    public FeedbackTab() {
        add(initializeTab());
    }

    @Override
    public TabsData getTabData() {
        return feedbackTabData;
    }

    @Override
    public Tab getInstance() {
        return this;
    }
}
