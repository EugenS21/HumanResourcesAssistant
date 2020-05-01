package com.humanresources.assistant.ui.signin;

import static com.vaadin.flow.component.Key.ENTER;
import static com.vaadin.flow.component.button.ButtonVariant.LUMO_PRIMARY;
import static com.vaadin.flow.component.notification.Notification.show;
import static com.vaadin.flow.component.notification.NotificationVariant.LUMO_ERROR;
import static com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode.CENTER;

import com.humanresources.assistant.backend.authentication.AccessData;
import com.humanresources.assistant.ui.signup.SignUpForm;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route ("signin")
@PageTitle ("Login to the site")
@CssImport ("./styles/shared-styles.css")
public class LoginScreen extends VerticalLayout {

    final TextField username;
    final PasswordField password;

    @Autowired
    private AccessData accessData;
    @Autowired
    private SignUpForm signUpForm;

    public LoginScreen() {
        setClassName("login-screen");
        setJustifyContentMode(CENTER);
        setAlignItems(Alignment.CENTER);
        setHorizontalComponentAlignment(Alignment.CENTER);
        setSizeFull();

        // TODO remove hardcoded values and add a static width var
        username = new TextField("Username");
        username.setWidth("300px");
//        username.setValue("eugen");
        username.setPlaceholder("Username");
        password = new PasswordField("Password");
        password.setWidthFull();
//        password.setValue("eugen123");
        password.setWidth("300px");
        password.setPlaceholder("Password");

        final Button login = new Button("Log in");
        login.addThemeVariants(LUMO_PRIMARY);
        login.addClickListener(this::login);
        login.addClickShortcut(ENTER);
        login.setWidth("300px");

        final Button signUp = new Button("Sign up");
        signUp.setWidth("300px");
        signUp.addClickListener(this::signup);

        add(username, password, login, signUp);
    }

    private void signup(ClickEvent<Button> buttonClickEvent) {
        signUpForm.addDialogCloseActionListener(event -> remove(signUpForm));
        add(signUpForm);
        signUpForm.open();
    }

    private void login(ClickEvent<Button> event) {
        if (accessData.signIn(username.getValue(), password.getValue())) {
            getUI().get().navigate("");
        } else {
            show("Invalid username or password").addThemeVariants(LUMO_ERROR);
        }
    }
}
