package com.humanresources.assistant.ui.requests.grid;

import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;

import com.humanresources.assistant.backend.dto.LeaveDto;
import com.humanresources.assistant.backend.enums.LeaveTypeEnum;
import com.humanresources.assistant.backend.exceptions.ui.grid.GridItemNotFound;
import com.humanresources.assistant.ui.requests.grid.modal.LeaveModal;
import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.flow.component.HasValue.ValueChangeListener;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.selection.SelectionListener;
import java.time.LocalDate;
import java.util.List;

public class LeavesGrid extends Grid<LeaveDto> {

    private final ListDataProvider<LeaveDto> dataProvider;
    private final Grid.Column<LeaveDto> firstNameColumn;
    private final Grid.Column<LeaveDto> lastNameColumn;
    private final Grid.Column<LeaveDto> startDateColumn;
    private final Grid.Column<LeaveDto> endDateColumn;
    private final Grid.Column<LeaveDto> leaveTypeColumn;
    private final Grid.Column<LeaveDto> isApprovedColumn;
    private final Grid.Column<LeaveDto> description;
    private final Grid.Column<LeaveDto> approvedBy;
    private final Grid.Column<LeaveDto> approvedDate;
    private LeaveModal leaveModal;

    public LeavesGrid(List<LeaveDto> dataProvider, Boolean displayHrGrid) {

        this.dataProvider = new ListDataProvider<>(dataProvider);

        firstNameColumn = addColumn(LeaveDto::getSurName).setHeader("First Name");
        lastNameColumn = addColumn(LeaveDto::getName).setHeader("Last Name");
        startDateColumn = addColumn(LeaveDto::getStartDate).setHeader("Start Date");
        endDateColumn = addColumn(LeaveDto::getEndDate).setHeader("End Date");
        isApprovedColumn = addColumn(LeaveDto::getIsApproved).setHeader("Is Approved");
        approvedBy = addColumn(LeaveDto::getApprovedBy).setHeader("Approved By");
        approvedDate = addColumn(LeaveDto::getApprovedDate).setHeader("Approved Date");
        leaveTypeColumn = addColumn(LeaveDto::getLeaveType).setHeader("LeaveDto Type");
        description = addColumn(LeaveDto::getDescription).setHeader("Description");

        setDataProvider(this.dataProvider);
        if (displayHrGrid) {
            addSelectionListener(onSelection());
        } else {
            firstNameColumn.setVisible(false);
            lastNameColumn.setVisible(false);
        }

        initializeColumnFilters();
        addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
    }

    private SelectionListener<Grid<LeaveDto>, LeaveDto> onSelection() {
        return selectedRow -> {
            try {
                final LeaveDto selectedItem = selectedRow.getFirstSelectedItem()
                    .orElseThrow(() -> new GridItemNotFound("Unable to find the item you've just selected."));
                if (!selectedItem.getIsApproved()) {
                    leaveModal = new LeaveModal(selectedItem);
                    leaveModal.open();
                } else {
                    Notification.show("Request already approved").addThemeVariants(NotificationVariant.LUMO_ERROR);
                }
            } catch (GridItemNotFound ignored) { }
        };
    }

    private void initializeColumnFilters() {
        HeaderRow filterRow = appendHeaderRow();
        initializeFirstNameFilter(filterRow);
        initializeSecondNameFilter(filterRow);
        initializeStartDayFilter(filterRow);
        initializeEndDayFilter(filterRow);
        initializeLeaveDtoTypeFilter(filterRow);
        initializeIsApprovedFilter(filterRow);
    }

    private void initializeFirstNameFilter(HeaderRow filterRow) {
        TextField firstName = new TextField();
        final ValueChangeListener<ComponentValueChangeEvent<TextField, String>> valueChangeListener
            = event -> dataProvider.addFilter(request -> containsIgnoreCase(request.getSurName(), firstName.getValue()));

        firstName.addValueChangeListener(valueChangeListener);

        filterRow.getCell(firstNameColumn).setComponent(firstName);
        firstName.setSizeFull();
        firstName.setPlaceholder("Filter");
    }

    private void initializeSecondNameFilter(HeaderRow filterRow) {
        TextField secondName = new TextField();
        final ValueChangeListener<ComponentValueChangeEvent<TextField, String>> valueChangeListener
            = event -> dataProvider.addFilter(request -> containsIgnoreCase(request.getName(), secondName.getValue()));

        secondName.addValueChangeListener(valueChangeListener);

        filterRow.getCell(lastNameColumn).setComponent(secondName);
        secondName.setSizeFull();
        secondName.setPlaceholder("Filter");
    }

    private void initializeStartDayFilter(HeaderRow filterRow) {
        DatePicker startDay = new DatePicker();
        final ValueChangeListener<ComponentValueChangeEvent<DatePicker, LocalDate>> valueChangeListener
            = event -> dataProvider.addFilter(request -> request.getStartDate().isAfter(startDay.getValue()));

        startDay.addValueChangeListener(valueChangeListener);

        filterRow.getCell(startDateColumn).setComponent(startDay);
        startDay.setSizeFull();
        startDay.setPlaceholder("Filter");
    }

    private void initializeEndDayFilter(HeaderRow filterRow) {
        DatePicker endDay = new DatePicker();
        final ValueChangeListener<ComponentValueChangeEvent<DatePicker, LocalDate>> valueChangeListener
            = event -> dataProvider.addFilter(request -> request.getEndDate().isBefore(endDay.getValue()));

        endDay.addValueChangeListener(valueChangeListener);

        filterRow.getCell(endDateColumn).setComponent(endDay);
        endDay.setSizeFull();
        endDay.setPlaceholder("Filter");
    }

    private void initializeLeaveDtoTypeFilter(HeaderRow filterRow) {
        Select<LeaveTypeEnum> requestType = new Select<>(LeaveTypeEnum.values());
        final ValueChangeListener<ComponentValueChangeEvent<Select<LeaveTypeEnum>, LeaveTypeEnum>> valueChangeListener
            = event -> dataProvider.addFilter(request -> request.getLeaveType().equals(requestType.getValue()));

        requestType.addValueChangeListener(valueChangeListener);

        filterRow.getCell(leaveTypeColumn).setComponent(requestType);
        requestType.setSizeFull();
        requestType.setPlaceholder("Filter");
    }

    private void initializeIsApprovedFilter(HeaderRow filterRow) {
        final Checkbox isApproved = new Checkbox();
        final ValueChangeListener<ComponentValueChangeEvent<Checkbox, Boolean>> valueChangeListener
            = event -> dataProvider.addFilter(request -> request.getIsApproved().equals(isApproved.getValue()));

        isApproved.addValueChangeListener(valueChangeListener);

        filterRow.getCell(isApprovedColumn).setComponent(isApproved);
        isApproved.setSizeFull();
    }

}
