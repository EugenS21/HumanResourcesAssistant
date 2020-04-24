package com.humanresources.assistant.ui.crudgrids;

import static com.humanresources.assistant.ui.crudgrids.UsersCrud.VIEW_NAME;
import static java.util.Arrays.stream;
import static java.util.stream.Stream.of;
import static org.vaadin.crudui.crud.CrudOperation.values;

import com.humanresources.assistant.backend.dto.DepartmentDto;
import com.humanresources.assistant.restclient.service.DepartmentRestService;
import com.humanresources.assistant.ui.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.lang.reflect.Field;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.crudui.crud.CrudListener;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.CrudFormFactory;

@Route (value = "departments_list", layout = MainLayout.class)
@PageTitle (VIEW_NAME)
public class DepartmentsCrud extends VerticalLayout {

    public static final String VIEW_NAME = "Departments list";
    private final GridCrud<DepartmentDto> departmentGrid;

    @Autowired
    DepartmentRestService departmentService;

    public DepartmentsCrud() {
        setSizeFull();
        departmentGrid = getInitializedGrid();
        addListenerToCrud();
        add(departmentGrid);
    }

    private void addListenerToCrud() {
        this.departmentGrid.setCrudListener(
            new CrudListener<>() {
                @Override
                public Collection<DepartmentDto> findAll() {
                    return departmentService.getObjects();
                }

                @Override
                public DepartmentDto add(DepartmentDto departmentDto) {
                    return departmentService.postObject(departmentDto);
                }

                @Override
                public DepartmentDto update(DepartmentDto departmentDto) {
                    return departmentService.putObject(departmentDto.getId(), departmentDto);
                }

                @Override
                public void delete(DepartmentDto departmentDto) {
                    departmentService.deleteObject(String.valueOf(departmentDto.getId()));
                    departmentGrid.refreshGrid();
                }
            }
        );
    }

    private GridCrud<DepartmentDto> getInitializedGrid() {
        GridCrud<DepartmentDto> departmentGrid = new GridCrud<>(DepartmentDto.class);
        final String[] propertiesName = getPropertiesName();
        setColumnsVisibility(departmentGrid, propertiesName);
        setFieldsVisibilityInCrudOperations(departmentGrid, propertiesName);
        return departmentGrid;
    }

    private void setColumnsVisibility(GridCrud<DepartmentDto> departments, String[] propertiesName) {
        departments.getGrid().setColumns(propertiesName);
    }

    private void setFieldsVisibilityInCrudOperations(GridCrud<DepartmentDto> departmentGrid, String[] propertiesName) {
        final CrudFormFactory<DepartmentDto> crudFormFactory = departmentGrid.getCrudFormFactory();
        final String[] propertiesWithoutId = stream(propertiesName)
            .filter(property -> !property.equals("id"))
            .toArray(String[]::new);
        stream(values()).forEach(operation -> crudFormFactory.setVisibleProperties(operation, propertiesWithoutId));
    }

    private String[] getPropertiesName() {
        return of(DepartmentDto.class.getDeclaredFields())
            .filter(field -> field.getModifiers() == 2)
            .map(Field::getName)
            .toArray(String[]::new);
    }
}
