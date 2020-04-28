package com.humanresources.assistant.ui.userprofile;

import static com.vaadin.flow.component.button.ButtonVariant.LUMO_PRIMARY;
import static com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment.START;
import static com.vaadin.flow.component.textfield.TextFieldVariant.LUMO_SMALL;
import static com.vaadin.flow.data.value.ValueChangeMode.ON_CHANGE;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;

public class UserDetailsLayout extends VerticalLayout {

    private final String WIDTH = "400px";
    private final Button submit;
    private TextField firstName;
    private TextField secondName;
    private TextField emailField;
    private TextField numberField;
    private TextField birthDate;
    private TextField dateOfEmployment;
    private TextField salary;
    private TextField street;

    public UserDetailsLayout() {
        setHorizontalComponentAlignment(START);
        setSpacing(false);
        setWidth(WIDTH);
        initializeFields();
        submit = getInitializedButton();
        add(submit);
    }

    private Button getInitializedButton() {
        final Button button = new Button("Submit");
        button.addThemeVariants(LUMO_PRIMARY);
        button.addClickListener(onSubmit());
        return button;
    }

    private ComponentEventListener<ClickEvent<Button>> onSubmit() {
        return null;
    }

    private void initializeFields() {
        final List<Triple<TextField, String, Boolean>> fields = new ArrayList<>() {{
            add(new ImmutableTriple<>(firstName, "First Name", true));
            add(new ImmutableTriple<>(secondName, "Last Name", true));
            add(new ImmutableTriple<>(emailField, "Email", false));
            add(new ImmutableTriple<>(numberField, "Phone Number", true));
            add(new ImmutableTriple<>(birthDate, "Birth Date", true));
            add(new ImmutableTriple<>(dateOfEmployment, "Date of Employment", false));
            add(new ImmutableTriple<>(salary, "Salary", false));
            add(new ImmutableTriple<>(street, "Street", true));
        }};
        fields.forEach(field -> {
            final Triple<TextField, String, Boolean> componentProps = field;
            TextField component = componentProps.getLeft();
            String componentName = componentProps.getMiddle();
            Boolean isRequired = componentProps.getRight();
            component = new TextField(componentName);
            component.setRequiredIndicatorVisible(isRequired);
            component.addThemeVariants(LUMO_SMALL);
            component.setErrorMessage(componentName + " is required");
            component.setValueChangeMode(ON_CHANGE);
            component.setReadOnly(!isRequired);
            add(component);
        });
    }
}
