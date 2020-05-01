package com.humanresources.assistant.ui.requests.grid;

import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;

import com.humanresources.assistant.backend.enums.LeaveTypeEnum;
import com.humanresources.assistant.backend.exceptions.ui.grid.GridItemNotFound;
import com.humanresources.assistant.backend.model.Request;
import com.humanresources.assistant.ui.requests.grid.modal.RequestModal;
import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.flow.component.HasValue.ValueChangeListener;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.selection.SelectionListener;
import java.time.LocalDate;
import java.util.List;

public class RequestsGrid extends Grid<Request> {

    private final ListDataProvider<Request> dataProvider;
    private final Grid.Column<Request> firstNameColumn;
    private final Grid.Column<Request> lastNameColumn;
    private final Grid.Column<Request> startDateColumn;
    private final Grid.Column<Request> endDateColumn;
    private final Grid.Column<Request> requestTypeColumn;
    private RequestModal requestModal;

    public RequestsGrid(List<Request> dataProvider) {

        this.dataProvider = new ListDataProvider<>(dataProvider);

        setDataProvider(this.dataProvider);

        firstNameColumn = addColumn(Request::getFirstName).setHeader("First Name");
        lastNameColumn = addColumn(Request::getLastName).setHeader("Last Name");
        startDateColumn = addColumn(Request::getFirstDate).setHeader("Start Date");
        endDateColumn = addColumn(Request::getLastDate).setHeader("End Date");
        requestTypeColumn = addColumn(Request::getRequestType).setHeader("Leave Type");

        initializeColumnFilters();

        addSelectionListener(onSelection());
        addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
    }

    private SelectionListener<Grid<Request>, Request> onSelection() {
        return selectedRow -> {
            try {
                final Request selectedItem = selectedRow.getFirstSelectedItem()
                    .orElseThrow(() -> new GridItemNotFound("Unable to find the item you've just selected."));
                requestModal = new RequestModal(selectedItem);
                requestModal.open();
            } catch (GridItemNotFound ignored) {}
        };
    }

    private void initializeColumnFilters() {
        HeaderRow filterRow = appendHeaderRow();
        initializeFirstNameFilter(filterRow);
        initializeSecondNameFilter(filterRow);
        initializeStartDayFilter(filterRow);
        initializeEndDayFilter(filterRow);
        initializeRequestTypeFilter(filterRow);
    }

    private void initializeFirstNameFilter(HeaderRow filterRow) {
        TextField firstName = new TextField();
        final ValueChangeListener<ComponentValueChangeEvent<TextField, String>> valueChangeListener
            = event -> dataProvider.addFilter(request -> containsIgnoreCase(request.getFirstName(), firstName.getValue()));

        firstName.addValueChangeListener(valueChangeListener);

        filterRow.getCell(firstNameColumn).setComponent(firstName);
        firstName.setSizeFull();
        firstName.setPlaceholder("Filter");
    }

    private void initializeSecondNameFilter(HeaderRow filterRow) {
        TextField secondName = new TextField();
        final ValueChangeListener<ComponentValueChangeEvent<TextField, String>> valueChangeListener
            = event -> dataProvider.addFilter(request -> containsIgnoreCase(request.getLastName(), secondName.getValue()));

        secondName.addValueChangeListener(valueChangeListener);

        filterRow.getCell(lastNameColumn).setComponent(secondName);
        secondName.setSizeFull();
        secondName.setPlaceholder("Filter");
    }

    private void initializeStartDayFilter(HeaderRow filterRow) {
        DatePicker startDay = new DatePicker();
        final ValueChangeListener<ComponentValueChangeEvent<DatePicker, LocalDate>> valueChangeListener
            = event -> dataProvider.addFilter(request -> request.getFirstDate().isAfter(startDay.getValue()));

        startDay.addValueChangeListener(valueChangeListener);

        filterRow.getCell(startDateColumn).setComponent(startDay);
        startDay.setSizeFull();
        startDay.setPlaceholder("Filter");
    }

    private void initializeEndDayFilter(HeaderRow filterRow) {
        DatePicker endDay = new DatePicker();
        final ValueChangeListener<ComponentValueChangeEvent<DatePicker, LocalDate>> valueChangeListener
            = event -> dataProvider.addFilter(request -> request.getLastDate().isBefore(endDay.getValue()));

        endDay.addValueChangeListener(valueChangeListener);

        filterRow.getCell(endDateColumn).setComponent(endDay);
        endDay.setSizeFull();
        endDay.setPlaceholder("Filter");
    }

    private void initializeRequestTypeFilter(HeaderRow filterRow) {
        Select<LeaveTypeEnum> requestType = new Select<>(LeaveTypeEnum.values());
        final ValueChangeListener<ComponentValueChangeEvent<Select<LeaveTypeEnum>, LeaveTypeEnum>> valueChangeListener
            = event -> dataProvider.addFilter(request -> request.getRequestType().equals(requestType.getValue()));

        requestType.addValueChangeListener(valueChangeListener);

        filterRow.getCell(requestTypeColumn).setComponent(requestType);
        requestType.setSizeFull();
        requestType.setPlaceholder("Filter");
    }

}
