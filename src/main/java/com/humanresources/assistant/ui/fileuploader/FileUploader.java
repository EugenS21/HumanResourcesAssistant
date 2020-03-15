package com.humanresources.assistant.ui.fileuploader;

import static com.vaadin.flow.component.button.ButtonVariant.MATERIAL_CONTAINED;

import com.humanresources.assistant.backend.service.FileService;
import com.humanresources.assistant.ui.MainLayout;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "FilesUploader",
       layout = MainLayout.class)
@PageTitle("Upload the CV you want to be analyzed")
public class FileUploader extends VerticalLayout {

    public static final String VIEW_NAME = "File Uploader";
    private final MultiFileMemoryBuffer memoryBuffer;
    private final Upload uploadContainer;
    private final Button uploadButton;
    private final Span informationContainer;

    @Autowired
    private FileService fileService;

    public FileUploader() {
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        memoryBuffer = new MultiFileMemoryBuffer();
        informationContainer = new Span();
        informationContainer.setText("Please upload your curriculum vitae in the container bellow");
        informationContainer.getStyle().set("font-size", "16px");
        uploadContainer = new Upload(memoryBuffer);
        uploadButton = new Button();

        uploadButton.addThemeVariants(MATERIAL_CONTAINED);
        uploadButton.setText("Upload CV");
        uploadButton.setWidth(uploadContainer.getWidth());

        uploadContainer.setAcceptedFileTypes("file/pdf");
        uploadContainer.setMaxFiles(1);
        uploadContainer.setDropLabel(new Label("Upload your CV in pdf format"));
        informationContainer.setWidth(uploadContainer.getWidth());
        informationContainer.getStyle().set("font-size", "14px");

        add(informationContainer);
        add(uploadContainer);
        add(uploadButton);

        uploadButton.addClickListener(
            (ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> memoryBuffer.getFiles()
                .forEach(
                    file -> {
                        Notification notification = new Notification(file);
                        notification.open();
                        notification.setText(file);
                    }
                ));
    }
}
