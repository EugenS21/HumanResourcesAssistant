package com.humanresources.assistant.ui.fileuploader;

import static com.humanresources.assistant.backend.enums.DepartmentEnum.values;
import static com.vaadin.flow.component.button.ButtonVariant.LUMO_PRIMARY;
import static com.vaadin.flow.component.icon.VaadinIcon.UPLOAD;
import static com.vaadin.flow.data.value.ValueChangeMode.ON_CHANGE;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.of;

import com.humanresources.assistant.backend.dto.FileDto;
import com.humanresources.assistant.backend.enums.DepartmentEnum;
import com.humanresources.assistant.restclient.multipart.MultipartFile;
import com.humanresources.assistant.restclient.service.FileRestService;
import com.humanresources.assistant.sshclients.model.EmployeeFile;
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
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Receiver;
import com.vaadin.flow.component.upload.SucceededEvent;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.InputStreamFactory;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import org.springframework.beans.factory.annotation.Autowired;

@Route (value = "cv_files_uploader", layout = MainLayout.class)
@PageTitle ("Upload the CV you want to be analyzed")
public class FileUploader extends VerticalLayout implements ComponentEventListener<SucceededEvent>, Receiver {

    public static final String VIEW_NAME = "File Uploader";
    private static final String WIDTH = "420px";

    private final TextField phoneNumber;
    private final TextArea textArea;
    private final ListBox<String> department;
    private final ByteArrayOutputStream byteArrayOutputStream;
    private final Upload uploadContainer;
    @Autowired
    FileRestService fileRestService;
    private boolean fileUploaded;
    private EmployeeFile employeeFile;
    private String fileName;
    private String mimeType;

    public FileUploader() {
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        Span informationContainer = initializeTextContainer();
        Button uploadButton = initializeUploadButton();
        Details details = initializeOrderedList();
        phoneNumber = initializePhoneNumber();
        uploadContainer = initializeUploadContainer();
        department = initializeDepartment();
        textArea = initializeTextArea();
        byteArrayOutputStream = new ByteArrayOutputStream();

        add(informationContainer, details, uploadContainer, phoneNumber, department, textArea, uploadButton);
    }

    @Override
    public void onComponentEvent(SucceededEvent succeededEvent) {
        InputStreamFactory inputStreamFactory =
            (InputStreamFactory) () -> new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

        MultipartFile multipartFile = MultipartFile.builder()
            .byteStream(byteArrayOutputStream)
            .contentType(mimeType)
            .fileName(fileName)
            .build();

        employeeFile = EmployeeFile.builder()
            .fileName(fileName)
            .multipartFile(multipartFile)
            .build();
        fileUploaded = true;
    }

    @Override
    public OutputStream receiveUpload(String fileName, String mimeType) {
        this.fileName = fileName;
        this.mimeType = mimeType;
        byteArrayOutputStream.reset();
        return byteArrayOutputStream;
    }

    private ComponentEventListener<ClickEvent<Button>> buttonClickListener() {
        return (ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
            if (fileUploaded && !phoneNumber.isEmpty() && !department.isEmpty()) {
                FileDto fileDto = FileDto.builder()
                    .fileName(fileName)
                    .fileType(mimeType)
                    .phoneNumber(phoneNumber.getValue())
                    .department(department.getValue())
                    .description(textArea.getValue())
                    .build();
                fileRestService.postObjectWithFile(fileDto, employeeFile.getMultipartFile());
                fileUploaded = false;
            } else {
                Notification.show("Can't add your document, check that you've provided all the data")
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
            }

        };
    }

    private TextField initializePhoneNumber() {
        final TextField phoneNumber = new TextField();
        phoneNumber.setPlaceholder("Phone Number");
        phoneNumber.setPattern("^0[6|7][0|1|2|6|7|8|9][0-9]{6}$");
        phoneNumber.setErrorMessage("Please add your phone number so we can stay in touch");
        phoneNumber.setRequired(true);
        phoneNumber.setWidth(WIDTH);
        phoneNumber.setRequiredIndicatorVisible(true);
        phoneNumber.setValueChangeMode(ON_CHANGE);
        return phoneNumber;
    }

    private Button initializeUploadButton() {
        final Button uploadButton = new Button("Upload CV");
        uploadButton.addThemeVariants(LUMO_PRIMARY);
        uploadButton.setIcon(UPLOAD.create());
        uploadButton.addClickListener(buttonClickListener());
        uploadButton.setWidth(WIDTH);
        return uploadButton;
    }

    private Upload initializeUploadContainer() {
        final MemoryBuffer memoryBuffer = new MemoryBuffer();
        final Upload uploadContainer = new Upload(memoryBuffer);
        uploadContainer.setAcceptedFileTypes(
            "application/pdf",
            "application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        uploadContainer.setMaxFiles(1);
        uploadContainer.setDropLabel(new Label("Upload your CV in pdf format"));
        uploadContainer.addSucceededListener(this);
        uploadContainer.setReceiver(this);
        uploadContainer.setWidth(WIDTH);
        return uploadContainer;
    }

    private Span initializeTextContainer() {
        final Span informationContainer = new Span();
        informationContainer.setText("Please upload your CV in the container bellow");
        informationContainer.getStyle().set("font-size", "16px");
        informationContainer.getStyle().set("font-weight", "bold");
        informationContainer.setWidth(WIDTH);
        return informationContainer;
    }

    private Details initializeOrderedList() {
        final Details details = new Details();
        details.setSummaryText("Please read the following rules");
        details.addThemeVariants(DetailsVariant.REVERSE, DetailsVariant.FILLED);
        details.addContent(new ListItem("Your CV should be only in pdf format;"));
        details.addContent(new ListItem("You can't upload more than 1 file at once;"));
        details.addContent(new ListItem("EuroPass CV format is expected;"));
        details.addContent(new ListItem("After uploading the CV dont' forget to provide additional data."));
        return details;
    }

    private ListBox<String> initializeDepartment() {
        final ListBox<String> department = new ListBox<>();
        department.setItems(of(values()).map(DepartmentEnum::getName).collect(toList()));
        return department;
    }

    private TextArea initializeTextArea() {
        final TextArea textAreaVariant = new TextArea();
        textAreaVariant.getStyle().set("maxHeight", "150px");
        textAreaVariant.getStyle().set("minHeight", "50px");
        textAreaVariant.setMaxLength(200);
        textAreaVariant.setPlaceholder("Add your description here");
        textAreaVariant.setWidth(WIDTH);
        return textAreaVariant;
    }
}
