package com.humanresources.assistant.ui.userprofile.modals;

import com.humanresources.assistant.ui.userprofile.forms.LeaveForm;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dialog.Dialog;

public class UserLeaveRequestModal extends Dialog {

    private final LeaveForm leaveForm;

    public UserLeaveRequestModal() {
        ComponentUtil.setData(UI.getCurrent(), Dialog.class, this);
        setCloseOnEsc(true);
        setCloseOnOutsideClick(true);
        this.leaveForm = initializeLeaveForm();
        add(leaveForm);
    }

    private LeaveForm initializeLeaveForm() {
        return new LeaveForm();
    }
}
