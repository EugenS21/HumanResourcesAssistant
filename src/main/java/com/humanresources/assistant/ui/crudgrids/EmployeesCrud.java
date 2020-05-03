package com.humanresources.assistant.ui.crudgrids;

import static java.lang.reflect.Modifier.PRIVATE;
import static java.util.Arrays.stream;
import static java.util.stream.Stream.of;

import com.humanresources.assistant.backend.dto.DepartmentDto;
import com.humanresources.assistant.backend.dto.EmployeeDto;
import com.humanresources.assistant.backend.dto.GradeDto;
import com.humanresources.assistant.backend.dto.LocationDto;
import com.humanresources.assistant.backend.dto.ProjectDto;
import com.humanresources.assistant.backend.dto.UserDto;
import com.humanresources.assistant.backend.entity.EmployeeEntity;
import com.humanresources.assistant.restclient.service.DepartmentRestService;
import com.humanresources.assistant.restclient.service.EmployeeRestService;
import com.humanresources.assistant.restclient.service.GradeRestService;
import com.humanresources.assistant.restclient.service.LocationRestService;
import com.humanresources.assistant.restclient.service.ProjectRestService;
import com.humanresources.assistant.restclient.service.UserRestService;
import com.humanresources.assistant.ui.MainLayout;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.Route;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.crudui.crud.CrudListener;
import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.CrudFormFactory;

@Route (value = "employees_list", layout = MainLayout.class)
@SuppressWarnings ("unchecked")
public class EmployeesCrud extends VerticalLayout implements AfterNavigationObserver {

    public static final String VIEW_NAME = "Employees list";
    private final GridCrud<EmployeeDto> employeesGrid;
    @Autowired
    EmployeeRestService employeeRestService;
    @Autowired
    LocationRestService locationRestService;
    @Autowired
    ProjectRestService projectRestService;
    @Autowired
    GradeRestService gradeRestService;
    @Autowired
    UserRestService userRestService;
    private List<DepartmentDto> departments;

    @Autowired
    DepartmentRestService departmentRestService;
    private List<LocationDto> locations;
    private List<ProjectDto> projects;
    private List<GradeDto> grades;
    private List<UserDto> unregisteredUsers;

    public EmployeesCrud() {
        setSizeFull();
        employeesGrid = getInitializedGrid();
        addListenerToCrud();
        add(employeesGrid);
        departments = new ArrayList<>();
        locations = new ArrayList<>();
        projects = new ArrayList<>();
        grades = new ArrayList<>();
        unregisteredUsers = new ArrayList<>();
    }

    private void addListenerToCrud() {
        this.employeesGrid.setCrudListener(
            new CrudListener<>() {
                @Override
                public Collection<EmployeeDto> findAll() {
                    return employeeRestService.getObjects();
                }

                @Override
                public EmployeeDto add(EmployeeDto employee) {
                    employeeRestService.postObject(employee);
                    return employee;
                }

                @Override
                public EmployeeDto update(EmployeeDto employee) {
                    return employeeRestService.putObject(employee.getId(), employee);
                }

                @Override
                public void delete(EmployeeDto employee) {
                    employeeRestService.deleteObject(employee.getId().toString());
                }
            }

        );
    }


    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        departments = departmentRestService.getObjects();
        locations = locationRestService.getObjects();
        projects = projectRestService.getObjects();
        grades = gradeRestService.getObjects();
        unregisteredUsers = userRestService.getObjects();
    }

    private GridCrud<EmployeeDto> getInitializedGrid() {
        GridCrud<EmployeeDto> employeesGrid = new GridCrud<>(EmployeeDto.class);
        final String[] propertiesName = getPropertiesName();
        setColumnsVisibility(employeesGrid, propertiesName);
        setFieldsVisibilityInCrudOperations(employeesGrid, propertiesName);
        return employeesGrid;
    }

    private void setColumnsVisibility(GridCrud<EmployeeDto> employeesGrid, String[] propertiesName) {
        List<String> columnsToIgnore = new ArrayList<>() {{
            add("department"); add("location"); add("project"); add("grade"); add("user"); add("teamLead");
        }};
        Predicate<String> columnsToFilter = columnName -> !columnsToIgnore.contains(columnName);
        final String[] columnsToDisplay = stream(propertiesName).filter(columnsToFilter).toArray(String[]::new);
        employeesGrid.getGrid().setColumns(columnsToDisplay);
        employeesGrid.getGrid().addColumn(employee -> {
            Optional<EmployeeEntity> teamLead = Optional.ofNullable(employee.getTeamLead());
            return teamLead.map(team -> team.getFirstName() + " / " + team.getSecondName()).orElse(null);
        }).setHeader("Team Lead");
        employeesGrid.getGrid().addColumn(employee -> employee.getDepartment().getDepartment()).setHeader("Department");
        employeesGrid.getGrid().addColumn(employee -> employee.getLocation().getCity()).setHeader("Location");
        employeesGrid.getGrid().addColumn(employee -> employee.getProject().getProjectName()).setHeader("Project");
        employeesGrid.getGrid().addColumn(employee -> employee.getGrade().getGrade()).setHeader("Grade");
        employeesGrid.getGrid().addColumn(employee -> employee.getUser().getEmail()).setHeader("Email");
    }

    private void setFieldsVisibilityInCrudOperations(GridCrud<EmployeeDto> employeesGrid, String[] propertiesName) {
        final CrudFormFactory<EmployeeDto> crudFormFactory = employeesGrid.getCrudFormFactory();
        String[] deleteProperties = stream(propertiesName)
            .filter(property -> property.equals("firstName") || property.equals("secondName"))
            .toArray(String[]::new);
        stream(CrudOperation.values()).forEach(
            operation -> {
                switch (operation) {
                    case DELETE:
                        crudFormFactory.setVisibleProperties(operation, deleteProperties);
                        break;
                    case READ:
                        crudFormFactory.setVisibleProperties(operation, propertiesName);
                        break;
                    default:
                        crudFormFactory.setVisibleProperties(operation, propertiesName);
                        crudFormFactory.setFieldProvider("department", this::getDepartmentsSelect);
                        crudFormFactory.setFieldProvider("location", this::getLocationsSelect);
                        crudFormFactory.setFieldProvider("project", this::getProjectsSelect);
                        crudFormFactory.setFieldProvider("grade", this::getGradesSelect);
                        crudFormFactory.setFieldProvider("user", this::getEmailsSelect);
                        break;
                }
            }
        );
    }

    @SneakyThrows
    private Select<DepartmentDto> getDepartmentsSelect() {
        return getSelect(departments, DepartmentDto::getDepartment);
    }

    @SneakyThrows
    private Select<LocationDto> getLocationsSelect() {
        return getSelect(locations, LocationDto::getCity);
    }

    @SneakyThrows
    private Select<ProjectDto> getProjectsSelect() {
        return getSelect(projects, ProjectDto::getProjectName);
    }

    @SneakyThrows
    private Select<GradeDto> getGradesSelect() {
        return getSelect(grades, GradeDto::getGrade);
    }

    @SneakyThrows
    private Select<UserDto> getEmailsSelect() {
        return getSelect(unregisteredUsers, UserDto::getEmail);
    }

    @SneakyThrows
    private <T> Select<T> getSelect(List<T> items, ItemLabelGenerator<T> labelGenerator) {
        Select<T> newSelect = new Select<>();
        newSelect.setItems(items);
        newSelect.setItemLabelGenerator(labelGenerator);
        return newSelect;
    }

    private String[] getPropertiesName() {
        return of(EmployeeDto.class.getDeclaredFields())
            .filter(field -> field.getModifiers() == PRIVATE)
            .map(Field::getName)
            .filter(field -> !field.equals("id"))
            .toArray(String[]::new);
    }
}
