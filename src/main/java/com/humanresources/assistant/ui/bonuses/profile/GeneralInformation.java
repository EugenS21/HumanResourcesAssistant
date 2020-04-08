package com.humanresources.assistant.ui.bonuses.profile;

import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static org.apache.commons.lang3.StringUtils.capitalize;

import com.humanresources.assistant.backend.exceptions.MethodNotFound;
import com.humanresources.assistant.backend.model.uimodels.bonuses.profile.GeneralInformationFieldValues;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.SneakyThrows;

@Getter
public class GeneralInformation extends VerticalLayout {

    private static List<TextField> fieldsList;
    private TextField name;
    private TextField surname;
    private TextField phone;
    private TextField email;
    private TextField address;
    private TextField project;
    private TextField department;
    private TextField grade;

    public GeneralInformation(GeneralInformationFieldValues generalInformationFieldValues) {

        name = new TextField();
        surname = new TextField();
        phone = new TextField();
        email = new TextField();
        address = new TextField();
        project = new TextField();
        department = new TextField();
        grade = new TextField();

        initializeFields(generalInformationFieldValues);

        fieldsList.forEach(this::add);
    }

    @SneakyThrows
    private void initializeFields(GeneralInformationFieldValues generalInformationFieldValues) {
        int i = 0;
        fieldsList = new ArrayList<TextField>() {{
            addAll(asList(name, surname, phone, email, address, project, department, grade));
        }};

        for (TextField textField : fieldsList) {
            final String propertyName =
                capitalize(stream(GeneralInformation.class.getDeclaredFields())
                               .filter(property -> !property.getName().contains("List"))
                               .map(Field::getName)
                               .collect(Collectors.toList()).get(i++));
            final Method methodToInvoke = stream(GeneralInformationFieldValues.class.getMethods())
                .filter(method -> method.getName().contains(propertyName))
                .findFirst()
                .orElseThrow(() -> new MethodNotFound("Can't find getter for property " + propertyName));
            textField.setReadOnly(true);
            textField.setSizeFull();
            textField.setLabel(propertyName);
            textField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
            textField.setValue(methodToInvoke.invoke(generalInformationFieldValues).toString());
        }

    }
}
