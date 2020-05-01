package com.humanresources.assistant.ui.fileuploader;

import static com.vaadin.flow.component.button.ButtonVariant.MATERIAL_CONTAINED;
import static com.vaadin.flow.component.notification.Notification.show;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.of;

import com.humanresources.assistant.backend.enums.DepartmentEnum;
import com.humanresources.assistant.backend.service.FileService;
import com.humanresources.assistant.ui.MainLayout;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.SucceededEvent;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "files_uploader", layout = MainLayout.class)
@PageTitle("Upload the CV you want to be analyzed")
public class FileUploader extends VerticalLayout {

    public static final String VIEW_NAME = "File Uploader";
    private static final String WIDTH = "405px";
    private final MultiFileMemoryBuffer memoryBuffer;
    private final Upload uploadContainer;
    private final Button uploadButton;
    private final Span informationContainer;
    private final TextField personalData;
    private final TextField phoneNumber;
    private final ListBox<String> department;
    private final Details details;
    private final Span listTitle;
    private final TextArea textAreaVariant;

    @Autowired
    private FileService fileService;

    public FileUploader() {
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        memoryBuffer = new MultiFileMemoryBuffer();
        informationContainer = new Span();
        uploadContainer = new Upload(memoryBuffer);
        uploadButton = new Button();
        personalData = new TextField();
        phoneNumber = new TextField();
        department = new ListBox<>();
        details = new Details();
        listTitle = new Span();
        textAreaVariant = new TextArea();

        initializeOrderedList();

        initializeTextContainer();

        initializeTextFields();

        initializeUploadBoxComponents();

        initializeDepartment();

        initializeTextArea();

        add(informationContainer);
        add(details);
        add(uploadContainer);
        add(personalData);
        add(phoneNumber);
        add(listTitle);
        add(department);
        add(textAreaVariant);
        add(uploadButton);
    }

    private ComponentEventListener<ClickEvent<Button>> buttonClickListener() {
        return (ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
            if (personalData.isEmpty()) {
                show("Please add your name and surname");
            } else if (phoneNumber.isEmpty()) {
                show("Please add your phone number");
            } else if (department.getValue().isEmpty()) {
                show("Please select a position you want to apply");
            } else {
                memoryBuffer.getFiles().forEach(Notification::show);
            }
        };
    }

    private ComponentEventListener<SucceededEvent> onSuccessFileUpload() {
        return (ComponentEventListener<SucceededEvent>) uploadContainer -> {
            uploadButton.setEnabled(true);
            show("Your file was uploaded successfully!");
        };
    }

    private void initializeTextFields() {
        personalData.setPlaceholder("Name and surname");
        phoneNumber.setPlaceholder("Phone number");
        phoneNumber.setWidth(WIDTH);
        personalData.setWidth(WIDTH);
    }

    private void initializeUploadBoxComponents() {
        uploadButton.addThemeVariants(MATERIAL_CONTAINED);
        uploadButton.setText("Upload CV");
        uploadButton.addClickListener(buttonClickListener());
        uploadButton.setEnabled(false);
        uploadContainer.setAcceptedFileTypes("application/pdf");
        uploadContainer.setMaxFiles(1);
        uploadContainer.setDropLabel(new Label("Upload your CV in pdf format"));
        uploadContainer.setWidth(WIDTH);
        uploadContainer.addSucceededListener(onSuccessFileUpload());
        uploadButton.setWidth(WIDTH);

    }

    private void initializeTextContainer() {
        informationContainer.setText("Please upload your curriculum vitae in the container bellow");
        informationContainer.setWidth(WIDTH);
        informationContainer.getStyle().set("font-size", "16px");
        informationContainer.getStyle().set("font-weight", "bold");
    }

    private void initializeOrderedList() {
        details.setSummaryText("Please read the following rules");
        details.addThemeVariants(DetailsVariant.REVERSE, DetailsVariant.FILLED);
        details.addContent(new ListItem("Your CV should be only in pdf format;"));
        details.addContent(new ListItem("You can't upload more than 1 file at once;"));
        details.addContent(new ListItem("EuroPass CV format is expected;"));
        details.addContent(new ListItem("After uploading the CV dont' forget to provide additional data."));
    }

    private void initializeDepartment() {
        listTitle.setText("Chose one of available position");
        listTitle.getStyle().set("font-weight", "bold");
        department.setItems(of(DepartmentEnum.values()).map(DepartmentEnum::getName).collect(toList()));
        department.setVisible(true);
    }

    private void initializeTextArea() {
        textAreaVariant.setWidth(WIDTH);
        textAreaVariant.getStyle().set("maxHeight", "150px");
        textAreaVariant.getStyle().set("minHeight", "50px");
        textAreaVariant.setMaxLength(200);
        textAreaVariant.setPlaceholder("Add your description here");
    }
}
