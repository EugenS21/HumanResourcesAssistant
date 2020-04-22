package com.humanresources.assistant.ui.employees;

import static com.humanresources.assistant.backend.functions.GenericFunctions.convertDepartmentModelToEntity;
import static java.util.stream.Stream.of;

import com.humanresources.assistant.backend.entity.Employee;
import com.humanresources.assistant.restclient.service.DepartmentRestService;
import com.humanresources.assistant.ui.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.Route;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.CrudFormFactory;

@Route (value = "employees_list", layout = MainLayout.class)
public class EmployeesCrud extends VerticalLayout implements AfterNavigationObserver {

    public static final String VIEW_NAME = "Employees list";
    private final GridCrud<Employee> employeesGrid;

    @Autowired
    DepartmentRestService departmentRestService;

    private List<com.humanresources.assistant.restclient.model.Department> departmentList;
//    private List<Location> locationList;
//    private List<Project> projectList;
//    private List<Grade> gradeList;

    public EmployeesCrud() {
        setSizeFull();
        employeesGrid = getInitializedGrid();
        add(employeesGrid);
    }

//    private void addListenerToCrud() {
//        this.employeesGrid.setCrudListener(
//            new CrudListener<Employee>() {
//                @Override
//                public Collection<Employee> findAll() {
//                    // url, post, cookies, String.class, uriparams
//                    restService.exchange();
//                    return employeesService.getAllEmployees();
//                }
//
//                @Override
//                public Employee add(Employee employee) {
//                    employeesService.saveUser(employee);
//                    return employee;
//                }
//
//                @Override
//                public Employee update(Employee employee) {
//                    return null;
//                }
//
//                @Override
//                public void delete(Employee employee) {
//
//                }
//            }
//
//        );
//    }


    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        departmentList = departmentRestService.getObjects();
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
                        final List<com.humanresources.assistant.backend.entity.Department> departmentsEntities
                            = convertDepartmentModelToEntity.apply(departmentList);
                        crudFormFactory.setFieldProvider("department", () -> {
                            Select<com.humanresources.assistant.backend.entity.Department> departments = new Select<>();
                            departments.setItems(departmentsEntities);
                            departments.setItemLabelGenerator(com.humanresources.assistant.backend.entity.Department::getName);
                            return departments;
                        });
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
