package com.humanresources.assistant;

import com.humanresources.assistant.backend.authentication.AccessControlFactory;
import com.humanresources.assistant.backend.authentication.AccessData;
import com.humanresources.assistant.ui.login.LoginScreen;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import com.vaadin.flow.spring.annotation.SpringComponent;

@SpringComponent
public class VaadinInitListener implements VaadinServiceInitListener {
    @Override
    public void serviceInit(ServiceInitEvent serviceInitEvent) {
        final AccessData accessData = AccessControlFactory.SINGLETONE.createAccessData();
        serviceInitEvent.getSource().addUIInitListener(uiInitEvent ->
                uiInitEvent.getUI().addBeforeEnterListener(enterEvent -> {
                    if (!accessData.isUserSignedIn() && !LoginScreen.class.equals(enterEvent.getNavigationTarget())) {
                        enterEvent.rerouteTo(LoginScreen.class);
                    }
                }));
    }
}
