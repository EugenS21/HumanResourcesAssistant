package com.humanresources.assistant.ui.crudgrids;

import static com.humanresources.assistant.ui.crudgrids.UsersCrud.VIEW_NAME;
import static java.lang.reflect.Modifier.PRIVATE;
import static java.util.Arrays.stream;
import static java.util.stream.Stream.of;

import com.humanresources.assistant.backend.dto.ClientDto;
import com.humanresources.assistant.backend.dto.ProjectDto;
import com.humanresources.assistant.restclient.service.ClientRestService;
import com.humanresources.assistant.restclient.service.ProjectRestService;
import com.humanresources.assistant.ui.MainLayout;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.crudui.crud.CrudListener;
import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.CrudFormFactory;

@Route (value = "projects_list", layout = MainLayout.class)
@PageTitle (VIEW_NAME)
public class ProjectCrud extends VerticalLayout implements AfterNavigationObserver {

    public static final String VIEW_NAME = "Projects list";
    private final GridCrud<ProjectDto> projectGrid;
    @Autowired
    ClientRestService clientRestService;
    @Autowired
    ProjectRestService projectRestService;
    private List<ClientDto> clientsList;

    public ProjectCrud() {
        setSizeFull();
        projectGrid = getInitializedGrid();
        addListenerToCrud();
        add(projectGrid);
    }

    private void addListenerToCrud() {
        this.projectGrid.setCrudListener(
            new CrudListener<>() {
                @Override
                public Collection<ProjectDto> findAll() {
                    return projectRestService.getObjects();
                }

                @Override
                public ProjectDto add(ProjectDto projectDto) {
                    return projectRestService.postObject(projectDto);
                }

                @Override
                public ProjectDto update(ProjectDto projectDto) {
                    return projectRestService.putObject(projectDto.getId(), projectDto);
                }

                @Override
                public void delete(ProjectDto projectDto) {
                    projectRestService.deleteObject(String.valueOf(projectDto.getId()));
                    projectGrid.refreshGrid();
                }
            }
        );
    }

    private GridCrud<ProjectDto> getInitializedGrid() {
        GridCrud<ProjectDto> projectGrid = new GridCrud<>(ProjectDto.class);
        final String[] propertiesName = getPropertiesName();
        projectGrid.getGrid().addThemeVariants(GridVariant.MATERIAL_COLUMN_DIVIDERS);
        projectGrid.getGrid().setMultiSort(true);
        projectGrid.setUpdateOperationVisible(false);
        projectGrid.setShowNotifications(true);
        projectGrid.setDeletedMessage("Selected item was removed successfully");
        projectGrid.setSavedMessage("Project was added successfully");
        setColumnsVisibility(projectGrid, propertiesName);
        setFieldsVisibilityInCrudOperations(projectGrid, propertiesName);
        return projectGrid;
    }

    private void setColumnsVisibility(GridCrud<ProjectDto> projects, String[] propertiesName) {
        projects.getGrid().setColumns(propertiesName);
        projects.getGrid()
            .addColumn(product -> {
                final ClientDto productClient = product.getClient();
                return productClient.getClientName() + " / " + productClient.getCountryName();
            })
            .setHeader("Client");
    }

    private void setFieldsVisibilityInCrudOperations(GridCrud<ProjectDto> projectGrid, String[] propertiesName) {
        final CrudFormFactory<ProjectDto> crudFormFactory = projectGrid.getCrudFormFactory();
        final String[] deleteFields = stream(propertiesName)
            .filter(property -> !property.equals("id"))
            .toArray(String[]::new);
        final List<String> propertiesWithClient = stream(deleteFields).collect(Collectors.toList());
        propertiesWithClient.add("client");
        final String[] propertiesWithClientColumn = propertiesWithClient.toArray(new String[0]);
        stream(CrudOperation.values()).forEach(
            operation -> {
                switch (operation) {
                    case DELETE:
                        crudFormFactory.setVisibleProperties(operation, deleteFields);
                        break;
                    case READ:
                        crudFormFactory.setVisibleProperties(operation, propertiesName);
                        break;
                    default:
                        crudFormFactory.setVisibleProperties(operation, propertiesWithClientColumn);
                        crudFormFactory.setFieldProvider("client", () -> {
                            Select<ClientDto> clients = new Select<>();
                            clients.setItems(clientsList);
                            clients.isRequiredIndicatorVisible();
                            clients.setItemLabelGenerator(client -> client.getClientName() + " / " + client.getCountryName());
                            return clients;
                        });
                        break;
                }
            });
    }

    private String[] getPropertiesName() {
        return of(ProjectDto.class.getDeclaredFields())
            .filter(field -> field.getModifiers() == PRIVATE)
            .filter(field -> !field.getName().equals("client") && !field.getName().equals("id"))
            .map(Field::getName)
            .toArray(String[]::new);
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        clientsList = clientRestService.getObjects();
    }
}
