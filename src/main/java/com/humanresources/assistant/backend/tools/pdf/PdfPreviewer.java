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
public class PdfPreviewer extends Component implements HasSize {

    public PdfPreviewer() {
        getElement().setAttribute("type", "application/pdf");
        setSizeFull();
        setHeightFull();
        setWidthFull();
    }

    public PdfPreviewer(String url) {
        this();
        getElement().setAttribute("data", url);
    }

    private PdfPreviewer(String fileToDisplay, FileInputStream fileInputStream) {
        this();
        StreamResource streamResource = new StreamResource(fileToDisplay, () -> fileInputStream);
        getElement().setAttribute("data", streamResource);
    }

    @SneakyThrows
    public static PdfPreviewer getInstance(File fileToDisplay) {
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(fileToDisplay);
            return new PdfPreviewer(fileToDisplay.getName(), fileInputStream);
        } catch (FileNotFoundException e) {
            throw new InstantiationException("Can't instantiate class because " + e.getMessage());
        }
    }
}
