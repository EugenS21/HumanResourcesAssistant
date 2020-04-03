package com.humanresources.assistant.backend.tools.other;

import static com.humanresources.assistant.backend.tools.other.Files.ABOUT_JOB;
import static com.humanresources.assistant.backend.tools.other.Files.BENEFITS;
import static com.humanresources.assistant.backend.tools.other.Files.COMPANY_DESCRIPTION;
import static com.humanresources.assistant.backend.tools.other.Files.REQUIREMENTS;
import static com.humanresources.assistant.backend.tools.other.Files.RESPONSIBILITIES;
import static com.humanresources.assistant.backend.tools.other.Files.TECHNOLOGIES;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.SneakyThrows;

@Builder
public final class FileData {

    public static List<String> JOB_FILE = getFileContent(ABOUT_JOB.getFile());
    public static List<String> RESPONSIBILITIES_FILE = getFileContent(RESPONSIBILITIES.getFile());
    public static List<String> REQUIREMENTS_FILE = getFileContent(REQUIREMENTS.getFile());
    public static List<String> TECHNOLOGIES_FILE = getFileContent(TECHNOLOGIES.getFile());
    public static List<String> BENEFITS_FILE = getFileContent(BENEFITS.getFile());
    public static List<String> COMPANY_DESCRIPTION_FILE = getFileContent(COMPANY_DESCRIPTION.getFile());

    @SneakyThrows
    private static List<String> getFileContent(File fileToRead) {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileToRead));
        List<String> contentLine = new ArrayList<>();
        String content;
        while ((content = bufferedReader.readLine()) != null) {
            contentLine.add(content);
        }
        bufferedReader.close();
        return contentLine;
    }
}
