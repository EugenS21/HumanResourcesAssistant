package com.humanresources.assistant.ui.crudgrids;

import static com.humanresources.assistant.ui.crudgrids.UsersCrud.VIEW_NAME;
import static java.lang.reflect.Modifier.PRIVATE;
import static java.util.Arrays.stream;
import static java.util.stream.Stream.of;
import static org.vaadin.crudui.crud.CrudOperation.values;

import com.humanresources.assistant.backend.dto.LocationDto;
import com.humanresources.assistant.restclient.service.LocationRestService;
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

@Route (value = "locations_list", layout = MainLayout.class)
@PageTitle (VIEW_NAME)
public class LocationCrud extends VerticalLayout {

    public static final String VIEW_NAME = "Locations list";
    private final GridCrud<LocationDto> locationGrid;

    @Autowired
    LocationRestService locationService;

    public LocationCrud() {
        setSizeFull();
        locationGrid = getInitializedGrid();
        addListenerToCrud();
        add(locationGrid);
    }

    private void addListenerToCrud() {
        this.locationGrid.setCrudListener(
            new CrudListener<>() {
                @Override
                public Collection<LocationDto> findAll() {
                    return locationService.getObjects();
                }

                @Override
                public LocationDto add(LocationDto locationDto) {
                    return locationService.postObject(locationDto);
                }

                @Override
                public LocationDto update(LocationDto locationDto) {
                    return locationService.putObject(locationDto.getId(), locationDto);
                }

                @Override
                public void delete(LocationDto locationDto) {
                    locationService.deleteObject(String.valueOf(locationDto.getId()));
                    locationGrid.refreshGrid();
                }
            }
        );
    }

    private GridCrud<LocationDto> getInitializedGrid() {
        GridCrud<LocationDto> locationGrid = new GridCrud<>(LocationDto.class);
        final String[] propertiesName = getPropertiesName();
        setColumnsVisibility(locationGrid, propertiesName);
        locationGrid.setUpdateOperationVisible(false);
        setFieldsVisibilityInCrudOperations(locationGrid, propertiesName);
        return locationGrid;
    }

    private void setColumnsVisibility(GridCrud<LocationDto> locations, String[] propertiesName) {
        locations.getGrid().setColumns(propertiesName);
    }

    private void setFieldsVisibilityInCrudOperations(GridCrud<LocationDto> locationGrid, String[] propertiesName) {
        final CrudFormFactory<LocationDto> crudFormFactory = locationGrid.getCrudFormFactory();
        final String[] propertiesWithoutId = stream(propertiesName)
            .filter(property -> !property.equals("id"))
            .toArray(String[]::new);
        stream(values()).forEach(operation -> crudFormFactory.setVisibleProperties(operation, propertiesWithoutId));
    }

    private String[] getPropertiesName() {
        return of(LocationDto.class.getDeclaredFields())
            .filter(field -> field.getModifiers() == PRIVATE && !field.getName().equals("id"))
            .map(Field::getName)
            .toArray(String[]::new);
    }
}
