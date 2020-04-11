package com.humanresources.assistant.ui.bonuses.modal.tabs;

import static com.humanresources.assistant.backend.enums.ui.TabsData.HISTORY;

import com.humanresources.assistant.backend.enums.ui.TabsData;
import com.vaadin.flow.component.tabs.Tab;

public class HistoryTab extends Tab implements ITabGenerics<Tab> {

    private final TabsData historyTabData = HISTORY;

    public HistoryTab() {
        add(initializeTab());
    }

    @Override
    public TabsData getTabData() {
        return historyTabData;
    }

    @Override
    public Tab getInstance() {
        return this;
    }
}
