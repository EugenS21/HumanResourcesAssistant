package com.humanresources.assistant.ui.fileuploader;

import com.humanresources.assistant.ui.MainLayout;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import static com.vaadin.flow.component.button.ButtonVariant.MATERIAL_CONTAINED;

@Route(value = "FilesUploader", layout = MainLayout.class)
@PageTitle("Upload the CV you want to be analyzed")
public class FileUploader extends VerticalLayout {

    public static final String VIEW_NAME = "File Uploader";
    private final MemoryBuffer memoryBuffer;
    private final Upload uploadContainer;
    private final Button uploadButton;
    private final Span informationContainer;

    public FileUploader() {
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        memoryBuffer = new MemoryBuffer();
        informationContainer = new Span();
        informationContainer.setText("Please upload your curriculum vitae in the container bellow:\n" +
                "- click on the 'Upload' button;\n" +
                "- browse for the file you want to upload;\n" +
                "- click on the 'Upload CV' button bellow the container\n" +
                "Only PDF files will be accepted");

        uploadContainer = new Upload(memoryBuffer);
        uploadButton = new Button();

        uploadButton.addThemeVariants(MATERIAL_CONTAINED);
        uploadButton.setText("Upload CV");
        uploadButton.setWidth(uploadContainer.getWidth());

        uploadContainer.setAcceptedFileTypes("file/png");
        uploadContainer.setMaxFiles(1);
        uploadContainer.setDropLabel(new Label("Upload your CV in pdf format"));
        informationContainer.setWidth(uploadContainer.getWidth());
        informationContainer.getStyle().set("font-size","14px");

        add(informationContainer);
        add(uploadContainer);
        add(uploadButton);
    }
}
