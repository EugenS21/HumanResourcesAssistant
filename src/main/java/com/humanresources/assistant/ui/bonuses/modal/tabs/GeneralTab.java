package com.humanresources.assistant.ui.bonuses.modal.tabs;

import static com.humanresources.assistant.backend.enums.ui.TabsData.GENERAL;

import com.humanresources.assistant.backend.enums.ui.TabsData;
import com.vaadin.flow.component.tabs.Tab;

public class GeneralTab extends Tab implements ITabGenerics<Tab> {

    private final TabsData generalTabData = GENERAL;

    public GeneralTab() {
        add(initializeTab());
    }

    @Override
    public TabsData getTabData() {
        return generalTabData;
    }

    @Override
    public Tab getInstance() {
        return this;
    }
}
