package com.humanresources.assistant.backend.tools.pdf;

import static com.itextpdf.text.BaseColor.DARK_GRAY;
import static com.itextpdf.text.FontFactory.COURIER_BOLD;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import lombok.Setter;
import lombok.SneakyThrows;

public class PdfFileGenerator {

    private final String PREFIX = "generated_cv";
    private final String SUFFIX = ".pdf";
    private final int HEADER_FONT_SIZE = 30;
    private final int BODY_FONT_SIZE = 14;

    private Document document;
    private File temporaryFile;
    @Setter
    private Font headerFont = FontFactory.getFont(COURIER_BOLD, HEADER_FONT_SIZE, DARK_GRAY);
    @Setter
    private Font bodyFont = FontFactory.getFont(COURIER_BOLD, BODY_FONT_SIZE, DARK_GRAY);

    public File getTemporaryPdfFile() {
        document.close();
        return temporaryFile;
    }

    @SneakyThrows
    public PdfFileGenerator() {
        document = new Document();
        temporaryFile = File.createTempFile(PREFIX, SUFFIX);
        PdfWriter.getInstance(document, new FileOutputStream(temporaryFile));
        document.open();
    }

    @SneakyThrows
    public PdfFileGenerator setHeader(String headerContent) {
        Paragraph header = new Paragraph(headerContent, headerFont);
        header.setAlignment(1);
        header.setSpacingAfter(10);
        document.add(header);
        return this;
    }

    @SneakyThrows
    public PdfFileGenerator setBody(String bodyContent) {
        Paragraph content = new Paragraph(bodyContent, bodyFont);
        content.setAlignment(0);
        content.setSpacingBefore(10);
        document.add(content);
        return this;
    }
}
