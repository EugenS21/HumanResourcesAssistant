package com.humanresources.assistant.ui.fileuploader;

import com.humanresources.assistant.ui.MainLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "FilesUploader", layout = MainLayout.class)
@PageTitle("Upload the CV you want to be analyzed")
public class FileUploader extends HorizontalLayout {

    public static final String VIEW_NAME = "File Uploader";
    private MemoryBuffer buffer = new MemoryBuffer();
    private Upload upload = new Upload(buffer);

    public FileUploader() {
        add(VaadinIcon.DIPLOMA.create());
        add(new Span(" Here should be a list of CVs which were sent by people "));
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);

        upload.setAcceptedFileTypes("file/png");
        upload.setMaxFiles(1);
        upload.setDropLabel(new Label("Upload your CV in pdf format"));

        add(upload);
    }
}
