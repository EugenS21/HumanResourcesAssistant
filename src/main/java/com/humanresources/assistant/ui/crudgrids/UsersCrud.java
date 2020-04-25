package com.humanresources.assistant.ui.crudgrids;

import static com.humanresources.assistant.ui.crudgrids.UsersCrud.VIEW_NAME;
import static java.lang.reflect.Modifier.PRIVATE;
import static java.util.Arrays.stream;
import static java.util.stream.Stream.of;

import com.humanresources.assistant.backend.dto.UserDto;
import com.humanresources.assistant.restclient.service.UserRestService;
import com.humanresources.assistant.ui.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.lang.reflect.Field;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.crudui.crud.CrudListener;
import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.CrudFormFactory;

@Route (value = "users_list", layout = MainLayout.class)
@PageTitle (VIEW_NAME)
public class UsersCrud extends VerticalLayout {

    public static final String VIEW_NAME = "Users list";
    private final GridCrud<UserDto> userGrid;

    @Autowired
    UserRestService userRestService;

    public UsersCrud() {
        setSizeFull();
        userGrid = getInitializedGrid();
        addListenerToCrud();
        add(userGrid);
    }

    private void addListenerToCrud() {
        this.userGrid.setCrudListener(
            new CrudListener<>() {
                @Override
                public Collection<UserDto> findAll() {
                    return userRestService.getObjects();
                }

                @Override
                public UserDto add(UserDto userDto) {
                    return null;
                }

                @Override
                public UserDto update(UserDto userDto) {
                    return null;
                }

                @Override
                public void delete(UserDto userDto) {
                    userRestService.deleteObject(String.valueOf(userDto.getId()));
                    userGrid.refreshGrid();
                }
            }
        );
    }

    private GridCrud<UserDto> getInitializedGrid() {
        GridCrud<UserDto> userGrid = new GridCrud<>(UserDto.class);
        final String[] propertiesName = getPropertiesName();
        setColumnsVisibility(userGrid, propertiesName);
        setFieldsVisibilityInCrudOperations(userGrid, propertiesName);
        userGrid.setAddOperationVisible(false);
        userGrid.setUpdateOperationVisible(false);
        return userGrid;
    }

    private void setColumnsVisibility(GridCrud<UserDto> users, String[] propertiesName) {
        users.getGrid().setColumns(propertiesName);
    }

    private void setFieldsVisibilityInCrudOperations(GridCrud<UserDto> userGrid, String[] propertiesName) {
        final CrudFormFactory<UserDto> crudFormFactory = userGrid.getCrudFormFactory();
        final String[] deleteFields = stream(propertiesName)
            .filter(property -> !property.equals("id"))
            .toArray(String[]::new);
        stream(CrudOperation.values()).forEach(
            operation -> {
                if (operation == CrudOperation.DELETE) {
                    crudFormFactory.setVisibleProperties(operation, deleteFields);
                } if (operation == CrudOperation.READ) {
                    crudFormFactory.setVisibleProperties(operation, propertiesName);
                }
            }
        );
    }

    private String[] getPropertiesName() {
        return of(UserDto.class.getDeclaredFields())
            .filter(field -> field.getModifiers() == PRIVATE)
            .map(Field::getName)
            .filter(field -> !field.equals("password") && !field.equals("id"))
            .toArray(String[]::new);
    }
}
