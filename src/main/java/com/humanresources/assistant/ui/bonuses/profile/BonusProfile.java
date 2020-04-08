package com.humanresources.assistant.ui.bonuses.profile;

import static com.vaadin.flow.component.icon.VaadinIcon.ARCHIVE;
import static com.vaadin.flow.component.icon.VaadinIcon.INFO;
import static com.vaadin.flow.component.icon.VaadinIcon.NOTEBOOK;

import com.humanresources.assistant.backend.enums.Department;
import com.humanresources.assistant.backend.enums.Grade;
import com.humanresources.assistant.backend.model.uimodels.bonuses.profile.GeneralInformationFieldValues;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.Tabs.Orientation;
import com.vaadin.flow.component.tabs.Tabs.SelectedChangeEvent;
import com.vaadin.flow.component.tabs.TabsVariant;
import java.util.HashMap;
import java.util.Map;

public class BonusProfile extends VerticalLayout {

    private final Tabs tabs;
    private final Tab general;
    private final Tab feedback;
    private final Tab history;
    private final GeneralInformation generalInformation;
    private final FeedbackInformation feedbackInformation;
    private final HistoryInformation historyInformation;
    private final Map<Tab, Component> tabsLayouts;
    private Component currentlySelectedPage;

    public BonusProfile() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        generalInformation = getGeneralInformation();
        feedbackInformation = getFeedBackInformation();
        historyInformation = getHistoryInformation();

        feedback = getFeedBackTab();
        general = getGeneralTab();
        history = getHistoryTab();
        tabs = initializeTabs();

        tabsLayouts = initializeTabsMap();

        add(tabs);
        add(generalInformation, feedbackInformation, historyInformation);
    }

    private FeedbackInformation getFeedBackInformation() {
        final FeedbackInformation feedbackInformation = new FeedbackInformation();
        feedbackInformation.setVisible(false);
        return feedbackInformation;
    }

    private HistoryInformation getHistoryInformation() {
        final HistoryInformation historyInformation = new HistoryInformation();
        historyInformation.setVisible(false);
        return historyInformation;
    }

    private GeneralInformation getGeneralInformation() {
        // TODO remove and add another mechanism of filling the form
        final GeneralInformationFieldValues temporaryInformation = GeneralInformationFieldValues.builder()
            .address("Studentilor")
            .department(Department.QA)
            .email("savcaeugen@gmail.com")
            .grade(Grade.A)
            .name("Savca")
            .surname("Eugeniu")
            .phone("+37368106878")
            .project("Project Custom")
            .build();
        final GeneralInformation generalInformation = new GeneralInformation(temporaryInformation);
        generalInformation.setVisible(true);
        return generalInformation;
    }

    private Tab getGeneralTab() {
        return new Tab(new HorizontalLayout(new Icon(INFO), new Span("General Information")));
    }

    private Tab getFeedBackTab() {
        return new Tab(new HorizontalLayout(new Icon(NOTEBOOK), new Span("Feedback")));
    }

    private Tab getHistoryTab() {
        return new Tab(new HorizontalLayout(new Icon(ARCHIVE), new Span("History")));
    }

    private Tabs initializeTabs() {
        final Tabs tabs = new Tabs(general, feedback, history);
        tabs.setOrientation(Orientation.HORIZONTAL);
        tabs.addThemeVariants(TabsVariant.LUMO_ICON_ON_TOP);
        tabs.setFlexGrowForEnclosedTabs(0.1);
        tabs.addSelectedChangeListener(getTabSelectedListener());
        tabs.addDetachListener(setDefaultTabOnClose());
        tabs.setSelectedTab(general);
        currentlySelectedPage = generalInformation;
        return tabs;
    }

    private Map<Tab, Component> initializeTabsMap() {
        return new HashMap<Tab, Component>() {{
            put(feedback, feedbackInformation);
            put(general, generalInformation);
            put(history, historyInformation);
        }};
    }

    private ComponentEventListener<DetachEvent> setDefaultTabOnClose() {
        return event -> tabs.setSelectedTab(general);
    }

    private ComponentEventListener<SelectedChangeEvent> getTabSelectedListener() {
        return event -> {
            final Component component = tabsLayouts.get(tabs.getSelectedTab());
            if (!component.equals(currentlySelectedPage)) {
                currentlySelectedPage.setVisible(false);
            }
            currentlySelectedPage = component;
            component.setVisible(true);
        };
    }

}
