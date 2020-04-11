package com.humanresources.assistant.ui.bonuses.modal.tabs;

import static com.vaadin.flow.component.tabs.Tabs.Orientation.HORIZONTAL;
import static com.vaadin.flow.component.tabs.TabsVariant.LUMO_CENTERED;

import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;

public class ModalTabs extends Tabs {

    public ModalTabs() {
        final Tab feedback = new FeedbackTab();
        final Tab general = new GeneralTab();
        final Tab history = new HistoryTab();
        add(general, feedback, history);
        setOrientation(HORIZONTAL);
        addThemeVariants(LUMO_CENTERED);
        setFlexGrowForEnclosedTabs(0.2);
    }

}
