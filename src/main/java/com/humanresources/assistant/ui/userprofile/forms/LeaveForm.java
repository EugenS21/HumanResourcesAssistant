package com.humanresources.assistant.ui.userprofile.forms;

import com.humanresources.assistant.backend.dto.LeaveDto;
import com.humanresources.assistant.backend.enums.LeaveTypeEnum;
import com.humanresources.assistant.restclient.service.LeavesRestService;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import java.time.LocalDate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

public class LeaveForm extends FormLayout {

    private final LeavesRestService leaveRest;
    private final Component component;
    private final DatePicker startDate;
    private final DatePicker endDate;
    private final Select<LeaveTypeEnum> leaveType;
    private final TextField description;
    private final Button addLeave;

    public LeaveForm() {
        setSizeFull();
        leaveRest = ComponentUtil.getData(UI.getCurrent(), LeavesRestService.class);
        this.component = ComponentUtil.getData(UI.getCurrent(), Dialog.class);
        startDate = initializeDatePicker("Start");
        endDate = initializeDatePicker("End");
        leaveType = initializeSelect();
        description = initializeDescription();
        addLeave = initializeButton();
        add(startDate, endDate, leaveType, description, addLeave);
    }

    private DatePicker initializeDatePicker(String dateType) {
        final DatePicker datePicker = new DatePicker();
        datePicker.setPlaceholder(String.format("Please select leave %s date", dateType));
        datePicker.setRequired(true);
        datePicker.setValue(LocalDate.now());
        datePicker.setInitialPosition(LocalDate.now());
        datePicker.setLabel(dateType + " date");
        datePicker.setErrorMessage(String.format("You need to select a %s date for a valid request", dateType));
        datePicker.setWeekNumbersVisible(true);
        datePicker.setRequiredIndicatorVisible(true);
        return datePicker;
    }

    private Select<LeaveTypeEnum> initializeSelect() {
        final Select<LeaveTypeEnum> leaveType = new Select<>();
        leaveType.setValue(LeaveTypeEnum.ANNUAL_LEAVE);
        leaveType.setLabel("Leave type");
        leaveType.setItems(LeaveTypeEnum.values());
        leaveType.setRequiredIndicatorVisible(true);
        return leaveType;
    }

    private TextField initializeDescription() {
        final TextField description = new TextField();
        description.setPlaceholder("Add here your description");
        description.setLabel("Description");
        return description;
    }

    private Button initializeButton() {
        final Button button = new Button();
        button.setText("Add leave");
        button.setIcon(VaadinIcon.PIN_POST.create());
        button.setAutofocus(true);
        button.addClickListener(postOnCreate());
        return button;
    }

    private ComponentEventListener<ClickEvent<Button>> postOnCreate() {
        return (ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
            if (!startDate.isInvalid() && !endDate.isInvalid() && !leaveType.isInvalid()) {
                try {
                    final LeaveDto leaveDto = LeaveDto.builder()
                        .startDate(startDate.getValue())
                        .endDate(endDate.getValue())
                        .leaveType(leaveType.getValue())
                        .description(description.getValue())
                        .isApproved(false)
                        .build();
                    leaveRest.postObject(leaveDto);
                    Notification.show("Leave added successfully")
                        .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                } catch (HttpClientErrorException | HttpServerErrorException ex) {
                    Notification.show("Could not process your request, try later")
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
                }

                ((Dialog) component).close();
            } else {
                Notification.show("Please fill form with required values")
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        };
    }
}
