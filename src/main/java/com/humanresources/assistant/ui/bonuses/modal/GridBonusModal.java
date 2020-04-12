package com.humanresources.assistant.ui.bonuses.modal;

import static com.humanresources.assistant.ui.bonuses.modal.ModalSizes.HEIGHT;
import static com.humanresources.assistant.ui.bonuses.modal.ModalSizes.WIDTH;
import static java.lang.String.format;

import com.humanresources.assistant.backend.exceptions.ui.grid.UnknownGridBonusModalTab;
import com.humanresources.assistant.backend.model.uimodels.bonuses.profile.BonusFields;
import com.humanresources.assistant.ui.bonuses.modal.tabpages.FeedbackInformation;
import com.humanresources.assistant.ui.bonuses.modal.tabpages.GeneralInformation;
import com.humanresources.assistant.ui.bonuses.modal.tabs.FeedbackTab;
import com.humanresources.assistant.ui.bonuses.modal.tabs.GeneralTab;
import com.humanresources.assistant.ui.bonuses.modal.tabs.HistoryTab;
import com.humanresources.assistant.ui.bonuses.modal.tabs.ModalBody;
import com.humanresources.assistant.ui.bonuses.modal.tabs.ModalTabs;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs.SelectedChangeEvent;
import lombok.Getter;
import lombok.SneakyThrows;

@CssImport(value = "styles/views/cardlist/card-list-view.css")
@Getter
public class GridBonusModal extends VerticalLayout {

    private final ModalTabs modalTabs;
    private ModalBody modalBody;

    public GridBonusModal(BonusFields gradeRiseFields) {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setWidth(WIDTH.getSize());
        setHeight(HEIGHT.getSize());
        modalTabs = new ModalTabs();
        modalTabs.addSelectedChangeListener(onTabChange(gradeRiseFields));
        addAttachListener(initializeDefaultTab(gradeRiseFields));
        add(modalTabs);
    }

    private ComponentEventListener<AttachEvent> initializeDefaultTab(BonusFields gradeRiseFields) {
        return event -> {
            final GeneralInformation generalInformationPage = new GeneralInformation(gradeRiseFields);
            modalBody = new ModalBody(generalInformationPage);
            add(modalBody);
        };
    }

    private ComponentEventListener<SelectedChangeEvent> onTabChange(BonusFields gradeRiseFields) {
        return tab -> {
            remove(modalBody);
            final Tab selectedTab = tab.getSelectedTab();
            modalBody = getInitializedPageBySpecificTab(selectedTab, gradeRiseFields);
            add(modalBody);
        };
    }

    @SneakyThrows
    private ModalBody getInitializedPageBySpecificTab(Tab selectedTab, BonusFields bonusFields) {
        if (selectedTab instanceof GeneralTab) {
            final GeneralInformation generalInformation = new GeneralInformation(bonusFields);
            modalBody = new ModalBody(generalInformation);
            return modalBody;
        } else if (selectedTab instanceof FeedbackTab) {
            final FeedbackInformation feedbackInformation = new FeedbackInformation();
            modalBody = new ModalBody(feedbackInformation);
            return modalBody;
        } else if (selectedTab instanceof HistoryTab) {
            return null;
        } else {
            final String exceptionMessage = format("Unknown tab [%s] from modal was selected", selectedTab.getLabel());
            throw new UnknownGridBonusModalTab(exceptionMessage);
        }
    }

}
