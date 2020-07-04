package com.humanresources.assistant.ui.userprofile;

import static com.humanresources.assistant.ui.userprofile.ProfileSettings.VIEW_NAME;

import com.humanresources.assistant.backend.dto.LeaveDto;
import com.humanresources.assistant.restclient.service.LeavesRestService;
import com.humanresources.assistant.ui.MainLayout;
import com.humanresources.assistant.ui.requests.grid.LeavesGrid;
import com.humanresources.assistant.ui.userprofile.button.AddLeave;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.BeforeLeaveEvent;
import com.vaadin.flow.router.BeforeLeaveObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@Route (value = "leaves_requests", layout = MainLayout.class)
@PageTitle (VIEW_NAME)
public class UserRequests extends VerticalLayout implements AfterNavigationObserver, BeforeLeaveObserver {

    public static final String VIEW_NAME = "Leaves";
    private final AddLeave addLeave;
    @Autowired
    private LeavesRestService leavesRestService;
    private LeavesGrid leavesGrid;

    public UserRequests() {
        addLeave = new AddLeave();
        add(addLeave);
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        ComponentUtil.setData(UI.getCurrent(), LeavesRestService.class, leavesRestService);
        List<LeaveDto> leaves = leavesRestService.getObjectsById();
        leavesGrid = new LeavesGrid(leaves, false);
        add(leavesGrid);
    }

    @Override
    public void beforeLeave(BeforeLeaveEvent beforeLeaveEvent) {
        remove(leavesGrid);
    }
}
