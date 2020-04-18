package com.humanresources.assistant;

import com.humanresources.assistant.backend.authentication.UserLogin;
import com.humanresources.assistant.ui.signin.LoginScreen;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
public class VaadinInitListener implements VaadinServiceInitListener {

    @Autowired
    private UserLogin user;

    @Override
    public void serviceInit(ServiceInitEvent serviceInitEvent) {
        serviceInitEvent.getSource().addUIInitListener(uiInitEvent ->
                                                           uiInitEvent.getUI().addBeforeEnterListener(enterEvent -> {
                                                               if (!user.isUserSignedIn()
                                                                   && !LoginScreen.class.equals(enterEvent.getNavigationTarget())) {
                                                                   enterEvent.rerouteTo(LoginScreen.class);
                                                               }
                                                           }));
    }
}
