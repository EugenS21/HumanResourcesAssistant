package com.humanresources.assistant.ui.employees;

import static java.util.stream.Stream.of;

import com.humanresources.assistant.backend.entity.Employee;
import com.humanresources.assistant.backend.service.EmployeesService;
import com.humanresources.assistant.ui.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.crudui.crud.CrudListener;
import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.CrudFormFactory;

@Route (value = "employees_list", layout = MainLayout.class)
public class EmployeesCrud extends VerticalLayout {

    public static final String VIEW_NAME = "Employees list";
    private final GridCrud<Employee> employeesGrid;

    @Autowired
    EmployeesService employeesService;

    public EmployeesCrud() {
        setSizeFull();
        employeesGrid = getInitializedGrid();
        add(employeesGrid);
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

    private GridCrud<Employee> getInitializedGrid() {
        GridCrud<Employee> employeesGrid = new GridCrud<>(Employee.class);
        final String[] propertiesName = getPropertiesName();
        setColumnsVisibility(employeesGrid, propertiesName);
        setFieldsVisibilityInCrudOperations(employeesGrid, propertiesName);
        return employeesGrid;
    }

    private void setColumnsVisibility(GridCrud<Employee> employeesGrid, String[] propertiesName) {
        employeesGrid.getGrid().setColumns(propertiesName);
    }

    private void setFieldsVisibilityInCrudOperations(GridCrud<Employee> employeesGrid, String[] propertiesName) {
        final CrudFormFactory<Employee> crudFormFactory = employeesGrid.getCrudFormFactory();
        String[] deleteProperties = Arrays.stream(propertiesName)
            .filter(property -> property.equals("firstName") || property.equals("secondName"))
            .toArray(String[]::new);
        Arrays.stream(CrudOperation.values()).forEach(
            operation -> {
                switch (operation) {
                    case ADD:
                        crudFormFactory.setVisibleProperties(operation, propertiesName);
                        break;
                    case DELETE:
                        crudFormFactory.setVisibleProperties(operation, deleteProperties);
                        break;
                    case READ:
                        crudFormFactory.setVisibleProperties(operation, propertiesName);
                        break;
                    default:

                }
            }
        );
    }

    private String[] getPropertiesName() {
        return of(Employee.class.getDeclaredFields())
            .filter(field -> field.getModifiers() == 2)
            .map(Field::getName)
            .filter(field -> !field.equals("id") && !field.equals("user"))
            .toArray(String[]::new);
    }
}
