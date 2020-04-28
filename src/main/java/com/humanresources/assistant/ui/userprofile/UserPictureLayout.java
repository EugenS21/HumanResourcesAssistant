package com.humanresources.assistant.ui.userprofile;

import static com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment.START;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Receiver;
import com.vaadin.flow.component.upload.SucceededEvent;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.server.InputStreamFactory;
import com.vaadin.flow.server.StreamResource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class UserPictureLayout extends VerticalLayout implements AfterNavigationObserver,
    Receiver, ComponentEventListener<SucceededEvent> {

    private final String WIDTH = "200px";
    private final Image userPicture;

    private final ByteArrayOutputStream byteArrayOutputStream;
    private String fileName;

    public UserPictureLayout() {
        setWidth(WIDTH);
        setHorizontalComponentAlignment(START);
        setSpacing(true);
        userPicture = getInitializedUserPicture();
        Upload uploader = getInitializedUploadForm();
        byteArrayOutputStream = new ByteArrayOutputStream(10240);
        add(userPicture, uploader);
    }

    private Image getInitializedUserPicture() {
        Image image = new Image();
        image.setWidth(WIDTH);
        image.setHeight(image.getWidth());
        image.getStyle().set("border-radius", "50%");
        return image;
    }

    private Button getInitializedBrowseButton() {
        Button button = new Button();
        button.setText("Browse");
        return button;
    }

    private Upload getInitializedUploadForm() {
        Label label = new Label();
        label.setText(null);
        Upload upload = new Upload();
        upload.setWidth("170px");
        upload.setAcceptedFileTypes("image/png,image/jpeg");
        upload.setDropLabel(label);
        upload.setUploadButton(getInitializedBrowseButton());
        upload.setReceiver(this);
        upload.addSucceededListener(this);
        return upload;
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        String pictureSrc = "https://clipartart.com/images/profile-image-icon-clipart-1.png";
        userPicture.setSrc(pictureSrc);
    }

    @Override
    public OutputStream receiveUpload(String fileName, String mimeType) {
        this.fileName = fileName;
        byteArrayOutputStream.reset();
        return byteArrayOutputStream;
    }

    @Override
    public void onComponentEvent(SucceededEvent succeededEvent) {
        InputStream inputStream;
        InputStreamFactory inputStreamFactory =
            (InputStreamFactory) () -> new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

        userPicture.setSrc(new StreamResource(fileName, inputStreamFactory));
    }
}
