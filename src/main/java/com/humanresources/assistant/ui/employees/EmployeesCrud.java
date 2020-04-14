package com.humanresources.assistant.ui.employees;

import static java.util.stream.Stream.of;

import com.humanresources.assistant.backend.enums.Grade;
import com.humanresources.assistant.backend.model.Employee;
import com.humanresources.assistant.backend.service.EmployeesService;
import com.humanresources.assistant.ui.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.crudui.crud.CrudListener;
import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.CrudFormFactory;

@Route(value = "employees", layout = MainLayout.class)
@PageTitle("Employees List")
public class EmployeesCrud extends VerticalLayout {

    public static final String VIEW_NAME = "Employees list";
    private final GridCrud<Employee> employeesGrid;
    @Autowired
    EmployeesService employeesService;

    public EmployeesCrud() {
        setSizeFull();
        final String[] properties = of(Employee.class.getDeclaredFields())
            .filter(field -> field.getModifiers() == 2)
            .map(Field::getName)
            .filter(field -> !field.equals("id"))
            .toArray(String[]::new);

        Select<Grade> grades = new Select<>();
        grades.setItems(Grade.values());

        employeesGrid = new GridCrud<>(Employee.class);
        employeesGrid.getGrid().setColumns(properties);
        setVisibilityForAllCrudOperations(employeesGrid.getCrudFormFactory(), properties);

        add(employeesGrid);
        addListenerToCrud();
    }

    private void setVisibilityForAllCrudOperations(CrudFormFactory<Employee> employeeCrudFormFactory, String[] properties) {
        Stream.of(CrudOperation.values())
            .forEach(operation -> employeeCrudFormFactory.setVisibleProperties(operation, properties));
    }

    private void addListenerToCrud() {
        this.employeesGrid.setCrudListener(
            new CrudListener<Employee>() {
                @Override
                public Collection<Employee> findAll() {
                    return employeesService.getAllEmployees();
                }

                @Override
                public Employee add(Employee employee) {
                    employeesService.saveUser(employee);
                    return employee;
                }

                @Override
                public Employee update(Employee employee) {
                    return null;
                }

                @Override
                public void delete(Employee employee) {

                }
            }

        );
    }


}
