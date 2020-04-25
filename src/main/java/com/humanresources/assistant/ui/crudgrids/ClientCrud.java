package com.humanresources.assistant.ui.crudgrids;

import static com.humanresources.assistant.ui.crudgrids.UsersCrud.VIEW_NAME;
import static java.lang.reflect.Modifier.PRIVATE;
import static java.util.Arrays.stream;
import static java.util.stream.Stream.of;

import com.humanresources.assistant.backend.dto.ClientDto;
import com.humanresources.assistant.restclient.service.ClientRestService;
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

@Route (value = "clients_list", layout = MainLayout.class)
@PageTitle (VIEW_NAME)
public class ClientCrud extends VerticalLayout {

    public static final String VIEW_NAME = "Clients list";
    private final GridCrud<ClientDto> clientGrid;

    @Autowired
    ClientRestService clientRestService;

    public ClientCrud() {
        setSizeFull();
        clientGrid = getInitializedGrid();
        addListenerToCrud();
        add(clientGrid);
    }

    private void addListenerToCrud() {
        this.clientGrid.setCrudListener(
            new CrudListener<>() {
                @Override
                public Collection<ClientDto> findAll() {
                    return clientRestService.getObjects();
                }

                @Override
                public ClientDto add(ClientDto clientDto) {
                    return clientRestService.postObject(clientDto);
                }

                @Override
                public ClientDto update(ClientDto clientDto) {
                    return clientRestService.putObject(clientDto.getId(), clientDto);
                }

                @Override
                public void delete(ClientDto clientDto) {
                    clientRestService.deleteObject(String.valueOf(clientDto.getId()));
                    clientGrid.refreshGrid();
                }
            }
        );
    }

    private GridCrud<ClientDto> getInitializedGrid() {
        GridCrud<ClientDto> clientGrid = new GridCrud<>(ClientDto.class);
        final String[] propertiesName = getPropertiesName();
        setColumnsVisibility(clientGrid, propertiesName);
        setFieldsVisibilityInCrudOperations(clientGrid, propertiesName);
        return clientGrid;
    }

    private void setColumnsVisibility(GridCrud<ClientDto> clients, String[] propertiesName) {
        clients.getGrid().setColumns(propertiesName);
    }

    private void setFieldsVisibilityInCrudOperations(GridCrud<ClientDto> clientGrid, String[] propertiesName) {
        final CrudFormFactory<ClientDto> crudFormFactory = clientGrid.getCrudFormFactory();
        final String[] deleteFields = stream(propertiesName)
            .filter(property -> !property.equals("id"))
            .toArray(String[]::new);
        stream(CrudOperation.values()).forEach(
            operation -> crudFormFactory.setVisibleProperties(operation, deleteFields));
    }

    private String[] getPropertiesName() {
        return of(ClientDto.class.getDeclaredFields())
            .filter(field -> field.getModifiers() == PRIVATE)
            .map(Field::getName)
            .toArray(String[]::new);
    }
}
