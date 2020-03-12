package com.humanresources.assistant.ui.login;

import com.humanresources.assistant.backend.authentication.AccessControlFactory;
import com.humanresources.assistant.backend.authentication.AccessData;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("Login")
@PageTitle("Login to the site")
@CssImport("./styles/shared-styles.css")
public class LoginScreen extends FlexLayout {
    private AccessData accessData = AccessControlFactory.SINGLETONE.createAccessData();

    public LoginScreen(){
        setSizeFull();
        setClassName("login-screen");

        LoginForm loginForm = new LoginForm();
        loginForm.addLoginListener(this::login);
        loginForm.addForgotPasswordListener(event -> Notification.show("Hint: same as username"));

        FlexLayout centeringLayout = new FlexLayout();
        centeringLayout.setSizeFull();
        centeringLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        centeringLayout.setAlignItems(Alignment.CENTER);
        centeringLayout.add(loginForm);
        add(centeringLayout);
    }

    private void login(LoginForm.LoginEvent event) {
        if (accessData.signIn(event.getUsername(), event.getPassword())) {
            getUI().get().navigate("");
        } else {
            event.getSource().setError(true);
        }
    }
}
