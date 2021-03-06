package com.humanresources.assistant.ui.requests;

import com.humanresources.assistant.backend.dto.LeaveDto;
import com.humanresources.assistant.restclient.service.LeavesRestService;
import com.humanresources.assistant.ui.MainLayout;
import com.humanresources.assistant.ui.requests.grid.LeavesGrid;
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

@Route (value = "leaves_management", layout = MainLayout.class)
@PageTitle ("Leaves Management")
public class LeavesManagement extends VerticalLayout implements AfterNavigationObserver, BeforeLeaveObserver {

    public static final String VIEW_NAME = "Leaves";
    private LeavesGrid requestsGrid;

    @Autowired
    private LeavesRestService leavesRestService;

    public LeavesManagement() {
        setSizeFull();
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        ComponentUtil.setData(UI.getCurrent(), LeavesRestService.class, leavesRestService);
        List<LeaveDto> leaves = leavesRestService.getObjects();
        requestsGrid = new LeavesGrid(leaves, true);
        add(requestsGrid);
    }

    @Override
    public void beforeLeave(BeforeLeaveEvent beforeLeaveEvent) {
        remove(requestsGrid);
    }
}
