package com.humanresources.assistant.ui.userdocs;

import static com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment.START;

import com.humanresources.assistant.ui.MainLayout;
import com.humanresources.assistant.ui.userdocs.grid.DocumentsGrid;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route (value = "docs_management", layout = MainLayout.class)
@PageTitle ("User Documents Management")
public class UserDocsManagement extends VerticalLayout {

    public static final String VIEW_NAME = "User Documents";

    private final DocumentsGrid documentsGrid;
    private final Button addNewDocument;
    private Dialog fileUploader;

    public UserDocsManagement() {
        documentsGrid = new DocumentsGrid();
        addNewDocument = getAddNewDocumentButton();
        setAlignItems(START);
        setHorizontalComponentAlignment(START);
        add(addNewDocument, documentsGrid);
    }

    private Button getAddNewDocumentButton() {
        final Button addNewDocumentButton = new Button();
        addNewDocumentButton.setText("Add New Document");
        addNewDocumentButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
//        addNewDocumentButton.addClickListener(()->)
        return addNewDocumentButton;
    }

    private Dialog getFileUploader() {
        final Dialog fileUploader = new Dialog();
        fileUploader.setCloseOnEsc(true);
        fileUploader.setCloseOnOutsideClick(true);

        return fileUploader;
    }
}
