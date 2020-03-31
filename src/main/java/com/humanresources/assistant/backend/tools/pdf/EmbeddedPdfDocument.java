package com.humanresources.assistant.backend.tools.pdf;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.server.StreamResource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import lombok.SneakyThrows;

@Tag("object")
public class EmbeddedPdfDocument extends Component implements HasSize {

    public EmbeddedPdfDocument() {
        getElement().setAttribute("type", "application/pdf");
        setSizeFull();
        setHeightFull();
        setWidthFull();
    }

    public EmbeddedPdfDocument(String url) {
        this();
        getElement().setAttribute("data", url);
    }

    private EmbeddedPdfDocument(String fileToDisplay, FileInputStream fileInputStream) {
        this();
        StreamResource streamResource = new StreamResource(fileToDisplay, () -> fileInputStream);
        getElement().setAttribute("data", streamResource);
    }

    @SneakyThrows
    public static EmbeddedPdfDocument getInstance(File fileToDisplay) {
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(fileToDisplay);
            return new EmbeddedPdfDocument(fileToDisplay.getName(), fileInputStream);
        } catch (FileNotFoundException e) {
            throw new InstantiationException("Can't instantiate class because " + e.getMessage());
        }
    }
}
