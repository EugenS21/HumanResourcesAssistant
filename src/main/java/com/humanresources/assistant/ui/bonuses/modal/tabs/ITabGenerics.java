package com.humanresources.assistant.ui.bonuses.modal.tabs;

import com.humanresources.assistant.backend.enums.ui.TabsData;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;

public interface ITabGenerics<T extends Tab> {

    TabsData getTabData();

    T getInstance();

    default HorizontalLayout initializeTab() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        final TabsData tabData = getTabData();
        final T tabInstance = getInstance();
        Icon tabIcon = new Icon(tabData.getIcon());
        Span tabName = new Span(tabData.getName());
        horizontalLayout.add(tabIcon, tabName);
        tabInstance.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        return horizontalLayout;
    }
}
