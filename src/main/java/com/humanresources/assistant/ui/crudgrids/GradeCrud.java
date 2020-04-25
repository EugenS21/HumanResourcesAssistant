package com.humanresources.assistant.ui.crudgrids;

import static com.humanresources.assistant.ui.crudgrids.UsersCrud.VIEW_NAME;
import static java.lang.reflect.Modifier.PRIVATE;
import static java.util.Arrays.stream;
import static java.util.stream.Stream.of;
import static org.vaadin.crudui.crud.CrudOperation.values;

import com.humanresources.assistant.backend.dto.GradeDto;
import com.humanresources.assistant.restclient.service.GradeRestService;
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

@Route (value = "grades_list", layout = MainLayout.class)
@PageTitle (VIEW_NAME)
public class GradeCrud extends VerticalLayout {

    public static final String VIEW_NAME = "Grades list";
    private final GridCrud<GradeDto> gradeGrid;

    @Autowired
    GradeRestService gradeService;

    public GradeCrud() {
        setSizeFull();
        gradeGrid = getInitializedGrid();
        addListenerToCrud();
        add(gradeGrid);
    }

    private void addListenerToCrud() {
        this.gradeGrid.setCrudListener(
            new CrudListener<>() {
                @Override
                public Collection<GradeDto> findAll() {
                    return gradeService.getObjects();
                }

                @Override
                public GradeDto add(GradeDto gradeDto) {
                    return gradeService.postObject(gradeDto);
                }

                @Override
                public GradeDto update(GradeDto gradeDto) {
                    return gradeService.putObject(gradeDto.getId(), gradeDto);
                }

                @Override
                public void delete(GradeDto gradeDto) {
                    gradeService.deleteObject(String.valueOf(gradeDto.getId()));
                    gradeGrid.refreshGrid();
                }
            }
        );
    }

    private GridCrud<GradeDto> getInitializedGrid() {
        GridCrud<GradeDto> gradeGrid = new GridCrud<>(GradeDto.class);
        final String[] propertiesName = getPropertiesName();
        setColumnsVisibility(gradeGrid, propertiesName);
        gradeGrid.setUpdateOperationVisible(false);
        setFieldsVisibilityInCrudOperations(gradeGrid, propertiesName);
        return gradeGrid;
    }

    private void setColumnsVisibility(GridCrud<GradeDto> grades, String[] propertiesName) {
        grades.getGrid().setColumns(propertiesName);
    }

    private void setFieldsVisibilityInCrudOperations(GridCrud<GradeDto> gradeGrid, String[] propertiesName) {
        final CrudFormFactory<GradeDto> crudFormFactory = gradeGrid.getCrudFormFactory();
        final String[] propertiesWithoutId = stream(propertiesName)
            .filter(property -> !property.equals("id"))
            .toArray(String[]::new);
        stream(values()).forEach(operation -> crudFormFactory.setVisibleProperties(operation, propertiesWithoutId));
    }

    private String[] getPropertiesName() {
        return of(GradeDto.class.getDeclaredFields())
            .filter(field -> field.getModifiers() == PRIVATE)
            .filter(field -> !field.getName().equals("id"))
            .map(Field::getName)
            .toArray(String[]::new);
    }
}
