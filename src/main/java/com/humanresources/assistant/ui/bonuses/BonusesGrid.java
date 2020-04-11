package com.humanresources.assistant.ui.bonuses;

import com.humanresources.assistant.backend.exceptions.GridCardDataException;
import com.humanresources.assistant.backend.exceptions.GridCardNotFound;
import com.humanresources.assistant.backend.model.uimodels.bonuses.profile.BonusFields;
import com.humanresources.assistant.ui.bonuses.modal.GridBonusModal;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.selection.SelectionListener;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BonusesGrid extends VerticalLayout {

    private static final Logger logger = LoggerFactory.getLogger(BonusesGrid.class);

    private final Grid<BonusFields> requestsGrid;

    public BonusesGrid(List<BonusFields> gridData) {
        setId("card-list-view");
        addClassNames("card-list-view");
        setSizeFull();
        requestsGrid = initializeGrid(gridData);
        add(requestsGrid);
    }

    private Grid<BonusFields> initializeGrid(List<BonusFields> gridData) {
        final Grid<BonusFields> grid = new Grid<>();
        grid.addThemeVariants(GridVariant.LUMO_COMPACT, GridVariant.LUMO_COMPACT);
        grid.addComponentColumn(this::createCard);
        grid.addSelectionListener(selectionListener());
        grid.setItems(gridData);
        return grid;
    }

    private HorizontalLayout createCard(BonusFields feedbackValues) {
        HorizontalLayout card = new HorizontalLayout();
        card.addClassName("card");
        card.setSpacing(false);

        Image pdfImage = new Image();
        pdfImage.setClassName("img");
        pdfImage.setSrc("http://icons.iconarchive.com/icons/treetog/file-type/256/pdf-icon.png");

        VerticalLayout cardBody = new VerticalLayout();
        cardBody.addClassName("description");
        cardBody.setPadding(false);
        cardBody.setSpacing(false);

        HorizontalLayout header = new HorizontalLayout();
        header.addClassName("header");
        header.setSpacing(false);
        String name = feedbackValues.getFirstName() + " " + feedbackValues.getLastName();
        Span feedbackPerson = new Span(name);
        feedbackPerson.setClassName("name");
        String personGrade = feedbackValues.getGrade().toString() + " " + feedbackValues.getDepartment().toString();
        Span personPosition = new Span(personGrade);
        personPosition.addClassName("date");
        header.add(feedbackPerson, personPosition);

        Span applicantDescription = new Span(feedbackValues.getMotivation());
        applicantDescription.addClassName("post");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String dateAndProject = feedbackValues.getLocalDateTime().format(formatter) + " " + feedbackValues.getProject();
        Span applicantPhone = new Span(dateAndProject);
        applicantPhone.addClassName("comments");

        HorizontalLayout jobData = new HorizontalLayout();
        jobData.add(applicantPhone);

        cardBody.add(header, applicantDescription, jobData);
        card.add(pdfImage, cardBody);
        return card;
    }

    private SelectionListener<Grid<BonusFields>, BonusFields> selectionListener() {
        return selection -> {
            try {
                final BonusFields gradeRiseFields = selection.getFirstSelectedItem().orElseThrow(GridCardNotFound::new);
                final Dialog newDialog = getNewModal(gradeRiseFields);
                add(newDialog);
                newDialog.open();
            } catch (GridCardNotFound exception) {
                logger.error("Grade Rise Request Grid does not contain such selected card");
                logger.error("Could not open details modal");
            }
        };
    }

    private Dialog getNewModal(BonusFields bonusFields) {
        Dialog dialog = getInitializedDialog();
        dialog.add(getNewBonusModal(bonusFields));
        return dialog;
    }

    @SneakyThrows
    private GridBonusModal getNewBonusModal(BonusFields bonusFields) {
        if (bonusFields != null) {
            return new GridBonusModal(bonusFields);
        } else {
            throw new GridCardDataException("No data provided by the selected card");
        }
    }

    private Dialog getInitializedDialog() {
        Dialog dialog = new Dialog();
        dialog.setSizeFull();
        return dialog;
    }
}
