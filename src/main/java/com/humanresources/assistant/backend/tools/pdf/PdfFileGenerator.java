package com.humanresources.assistant.backend.tools.pdf;

import static com.itextpdf.text.BaseColor.DARK_GRAY;
import static com.itextpdf.text.BaseColor.LIGHT_GRAY;
import static com.itextpdf.text.FontFactory.HELVETICA_BOLD;
import static com.itextpdf.text.FontFactory.TIMES_BOLD;
import static com.itextpdf.text.FontFactory.TIMES_ROMAN;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import lombok.SneakyThrows;

public class PdfFileGenerator {

    private final String PREFIX = "generated_cv";
    private final String SUFFIX = ".pdf";
    private final int HEADER_FONT_SIZE = 24;
    private final int BODY_FONT_SIZE = 12;

    private Document document;
    private File temporaryFile;
    private Font headerFont = FontFactory.getFont(HELVETICA_BOLD, HEADER_FONT_SIZE, DARK_GRAY);
    private Font bodyFont = FontFactory.getFont(TIMES_ROMAN, BODY_FONT_SIZE, LIGHT_GRAY);
    private Font bodyBoldFont = FontFactory.getFont(TIMES_BOLD, BODY_FONT_SIZE, DARK_GRAY);

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
    public PdfFileGenerator setCompanyData(String bodyContent) {
        Paragraph content = new Paragraph(bodyContent, bodyFont);
        content.setAlignment(0);
        document.add(content);
        return this;
    }

    @SneakyThrows
    public PdfFileGenerator setAboutJob(String aboutJobContent) {
        addParagraph("About Job", aboutJobContent);
        return this;
    }

    @SneakyThrows
    public PdfFileGenerator setRequirements(List<String> requirementsContent) {
        addList("Requirements", requirementsContent);
        return this;
    }

    @SneakyThrows
    public PdfFileGenerator setTechStack(List<String> techStackContent) {
        addList("Tech Stack", techStackContent);
        return this;
    }

    @SneakyThrows
    public PdfFileGenerator setResponsibilities(List<String> responsibilitiesContent) {
        addList("Responsibilities", responsibilitiesContent);
        return this;
    }

    @SneakyThrows
    public PdfFileGenerator setCompanyBenefits(List<String> companyBenefits) {
        addList("Company Benefits", companyBenefits);
        return this;
    }

    @SneakyThrows
    public PdfFileGenerator setFooter(String footerContent) {
        Paragraph content = new Paragraph(footerContent, headerFont);
        content.setAlignment(1);
        document.add(content);
        return this;
    }

    @SneakyThrows
    private void addList(String name, List<String> items) {
        com.itextpdf.text.List list = new com.itextpdf.text.List(com.itextpdf.text.List.NUMERICAL);
        items.forEach(element -> list.add(new ListItem(element, bodyFont)));
        Paragraph content = new Paragraph(name, bodyBoldFont);
        content.setAlignment(0);
        content.setSpacingBefore(10);
        document.add(content);
        document.add(list);
    }

    @SneakyThrows
    private void addParagraph(String title, String content) {
        Paragraph paragraphTitle = new Paragraph(title, bodyBoldFont);
        Paragraph paragrapthContent = new Paragraph(content, bodyFont);
        paragraphTitle.setAlignment(0);
        document.add(paragraphTitle);
        document.add(paragrapthContent);
    }
}
