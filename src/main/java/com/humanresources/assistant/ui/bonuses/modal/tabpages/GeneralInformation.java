package com.humanresources.assistant.ui.bonuses.modal.tabpages;

import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static org.apache.commons.lang3.StringUtils.capitalize;

import com.humanresources.assistant.backend.model.uimodels.bonuses.profile.BonusFields;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

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

    public GeneralInformation(BonusFields gradeRiseFields) {

        fieldsList = new ArrayList<>();

        name = new TextField();
        surname = new TextField();
        phone = new TextField();
        email = new TextField();
        address = new TextField();
        project = new TextField();
        department = new TextField();
        grade = new TextField();

        initializeFields(gradeRiseFields);
    }

    private void initializeFields(BonusFields gradeRiseFields) {
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
            textField.setReadOnly(true);
            textField.setSizeFull();
            textField.setLabel(propertyName);
            textField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        }

        name.setValue(gradeRiseFields.getFirstName());
        surname.setValue(gradeRiseFields.getLastName());
        phone.setValue("+373-68-105-811");
        email.setValue("s@gmail.com");
        address.setValue("Street1");
        project.setValue(gradeRiseFields.getProject());
        department.setValue(gradeRiseFields.getDepartment().getName());
        grade.setValue(gradeRiseFields.getGrade().getName());

        fieldsList.forEach(this::add);
    }

    ;
}
