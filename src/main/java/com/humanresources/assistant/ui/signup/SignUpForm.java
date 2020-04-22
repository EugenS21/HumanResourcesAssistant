package com.humanresources.assistant.ui.signup;

import static com.humanresources.assistant.backend.enums.ERole.ADMIN;
import static com.vaadin.flow.component.button.ButtonVariant.LUMO_LARGE;
import static com.vaadin.flow.component.notification.Notification.show;
import static com.vaadin.flow.component.notification.NotificationVariant.LUMO_ERROR;
import static com.vaadin.flow.component.notification.NotificationVariant.LUMO_SUCCESS;
import static com.vaadin.flow.component.textfield.TextFieldVariant.LUMO_ALIGN_CENTER;
import static java.util.Collections.singleton;

import com.humanresources.assistant.backend.payload.request.SignUpRequest;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class SignUpForm extends Dialog {

    private final VerticalLayout formBody;
    private final Span welcomeText;
    private final TextField username;
    private final PasswordField password;
    private final EmailField email;
    private final Button button;

    @Value ("${app.baseUrl}")
    private String baseUrl;
    @Autowired
    private RestTemplate restTemplate;

    public SignUpForm() {

        formBody = new VerticalLayout();
        welcomeText = new Span("Please provide your data");
        username = getNewTextField("Username");
        password = getNewPasswordField("Password");
        email = getNewEmailField("Corporate email");
        button = getNewButton();

        //TODO remove
        username.setValue("eugen");
        password.setValue("eugen123");
        email.setValue("eugen@ourcompany.com");

        formBody.add(welcomeText, username, password, email, button);
        add(formBody);
    }

    private Button getNewButton() {
        final Button button = new Button("Sign up");
        button.addClickListener(onButtonClick());
        button.setWidthFull();
        button.addThemeVariants(LUMO_LARGE);
        return button;
    }

    private ComponentEventListener<ClickEvent<Button>> onButtonClick() {
        return event -> {
            String signUpUrl = baseUrl + "signup";
            SignUpRequest signUpRequest = SignUpRequest.builder()
                .username(username.getValue())
                .password(password.getValue())
                .email(email.getValue())
                .roles(singleton(ADMIN.name()))
                .build();
            try {
                restTemplate.postForEntity(signUpUrl, signUpRequest, Object.class);
                show("User successfully created").addThemeVariants(LUMO_SUCCESS);
            } catch (HttpClientErrorException e) {
                show(e.getMessage()).addThemeVariants(LUMO_ERROR);
            }
        };
    }

    private TextField getNewTextField(String label) {
        final TextField textField = new TextField(label);
        textField.setErrorMessage("Your username doesn't follow our requirements or it's empty");
        textField.setPlaceholder(label);
        textField.addThemeVariants(LUMO_ALIGN_CENTER);
        return textField;
    }

    private PasswordField getNewPasswordField(String label) {
        final PasswordField passwordField = new PasswordField(label);
        passwordField.setPlaceholder("At least 6 chars");
        passwordField.setPlaceholder(label);
        passwordField.setPattern(".*");
        passwordField.setErrorMessage("Your password doesn't follow our requirements our it's empty");
        passwordField.addThemeVariants(LUMO_ALIGN_CENTER);
        return passwordField;
    }

    private EmailField getNewEmailField(String label) {
        final EmailField emailField = new EmailField(label);
        emailField.setPattern("[A-Za-z]+@ourcompany.com");
        emailField.setPlaceholder(label);
        emailField.setErrorMessage("Email containing digits is not allowed, also other domains than our company allow");
        emailField.addThemeVariants(LUMO_ALIGN_CENTER);

        return emailField;
    }
}
