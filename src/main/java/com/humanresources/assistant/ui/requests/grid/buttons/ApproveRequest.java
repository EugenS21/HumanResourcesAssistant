package com.humanresources.assistant.ui.requests.grid.buttons;

import com.humanresources.assistant.backend.dto.LeaveDto;
import com.humanresources.assistant.restclient.service.LeavesRestService;
import com.humanresources.assistant.ui.requests.grid.modal.LeaveModal;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

public class ApproveRequest extends Button {

    private final Logger log = LoggerFactory.getLogger(ApproveRequest.class);

    public ApproveRequest() {
        setText("Approve");
        setWidthFull();
        addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addClickListener(onClick());
    }

    private ComponentEventListener<ClickEvent<Button>> onClick() {
        return event -> {
            final LeavesRestService leavesRestService = ComponentUtil.getData(UI.getCurrent(), LeavesRestService.class);
            final LeaveDto leaveToUpdate = ComponentUtil.getData(UI.getCurrent(), LeaveDto.class);
            leaveToUpdate.setIsApproved(true);
            final LeaveModal requestModal = ComponentUtil.getData(UI.getCurrent(), LeaveModal.class);
            try {
                leavesRestService.putObject(leaveToUpdate.getId(), leaveToUpdate);
                requestModal.close();
                Notification.show("Leave approved").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            } catch (HttpClientErrorException | HttpServerErrorException exception) {
                Notification.show("Unable to approve the request try later")
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
                requestModal.close();
                log.error("Unable to approve the request " + exception.getResponseBodyAsString());
            }
        };
    }
}
