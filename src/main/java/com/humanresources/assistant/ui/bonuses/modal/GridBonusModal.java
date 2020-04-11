package com.humanresources.assistant.ui.bonuses.modal;

import com.humanresources.assistant.backend.model.uimodels.bonuses.profile.BonusFields;
import com.humanresources.assistant.ui.bonuses.modal.tabs.ModalTabs;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

@CssImport(value = "styles/views/cardlist/card-list-view.css")
public class GridBonusModal extends VerticalLayout {

    private final ModalTabs modalTabs;

//    private final GeneralInformation generalInformation;
//    private final FeedbackInformation feedbackInformation;
//    private final HistoryInformation historyInformation;
//    private final Map<Tab, Component> tabsLayouts;
//    private Component currentlySelectedPage;

    public GridBonusModal(BonusFields gradeRiseFields) {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        modalTabs = new ModalTabs();
        //TODO need here to put the initialization of page with specific content by specific tab
//        modalTabs.addSelectedChangeListener();
//        generalInformation = getGeneralInformation(gradeRiseFields);
//        feedbackInformation = getFeedBackInformation();
//        historyInformation = getHistoryInformation();
//
//        feedback = getFeedBackTab();
//        general = getGeneralTab();
//        history = getHistoryTab();
//        tabs = initializeTabs();
//
//        tabsLayouts = initializeTabsMap();
//
//        add(tabs);
//        add(generalInformation, feedbackInformation, historyInformation);
        add(modalTabs);
    }
//
//    private FeedbackInformation getFeedBackInformation() {
//        final FeedbackInformation feedbackInformation = new FeedbackInformation();
//        feedbackInformation.setVisible(false);
//        return feedbackInformation;
//    }
//
//    private HistoryInformation getHistoryInformation() {
//        final HistoryInformation historyInformation = new HistoryInformation();
//        historyInformation.setVisible(false);
//        return historyInformation;
//    }
//
//    private GeneralInformation getGeneralInformation(BonusFields gradeRiseFields) {
//        final GeneralInformation generalInformation = new GeneralInformation(gradeRiseFields);
//        generalInformation.setVisible(true);
//        return generalInformation;
//    }
//
//    private Tabs initializeTabs() {
//        final Tabs tabs = new Tabs(general, feedback, history);
//
//        tabs.addSelectedChangeListener(getTabSelectedListener());
//        tabs.addDetachListener(setDefaultTabOnClose());
//        tabs.setSelectedTab(general);
//        currentlySelectedPage = generalInformation;
//        return tabs;
//    }
//
//    private Map<Tab, Component> initializeTabsMap() {
//        return new HashMap<Tab, Component>() {{
//            put(feedback, feedbackInformation);
//            put(general, generalInformation);
//            put(history, historyInformation);
//        }};
//    }
//
//    private ComponentEventListener<DetachEvent> setDefaultTabOnClose() {
//        return event -> tabs.setSelectedTab(general);
//    }
//
//    private ComponentEventListener<SelectedChangeEvent> getTabSelectedListener() {
//        return event -> {
//            final Component component = tabsLayouts.get(tabs.getSelectedTab());
//            if (!component.equals(currentlySelectedPage)) {
//                currentlySelectedPage.setVisible(false);
//            }
//            currentlySelectedPage = component;
//            component.setVisible(true);
//        };
//    }

}
