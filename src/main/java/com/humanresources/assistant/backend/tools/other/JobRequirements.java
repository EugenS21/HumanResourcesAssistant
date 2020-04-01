package com.humanresources.assistant.backend.tools.other;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.tuple.Triple.of;

import com.humanresources.assistant.backend.enums.Department;
import com.humanresources.assistant.backend.enums.Grade;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import lombok.SneakyThrows;
import org.apache.commons.lang3.tuple.Triple;

public class JobRequirements {

    @SneakyThrows
    private static List<String> readSpecificFile(File fileToRead) {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileToRead));
        List<String> contentLine = new ArrayList<>();
        String content;
        while ((content = bufferedReader.readLine()) != null) {
            contentLine.add(content);
        }
        return contentLine;
    }

    private static Line getFileContent(List<String> lines) {
        Line linesDetails = new Line();
        lines.forEach(line -> {
            Pattern gradePattern = Pattern.compile("^\\w");
            Pattern departmentPattern = Pattern.compile("(?<=\\|)\\w+(?=\\|)");
            Pattern valuePattern = Pattern.compile("(?<=\\|)\\w+\\s+.*(?=;)");
            Matcher matcher = gradePattern.matcher(line);
            String grade = (matcher.find())
                           ? matcher.group(0)
                           : "";
            matcher = departmentPattern.matcher(line);
            String department = (matcher.find())
                                ? matcher.group(0)
                                : "";
            matcher = valuePattern.matcher(line);
            String description = (matcher.find())
                                 ? matcher.group(0)
                                 : "";
            linesDetails.addValues(grade, department, description);
        });
        return linesDetails;
    }

    private static final class Line {

        List<Triple<Grade, Department, String>> values;

        public Line() {
            values = new ArrayList<>();
        }

        public void addValues(String grade, String department, String value) {
            addConvertedValues(grade, department, value);
        }

        public List<Triple<Grade, Department, String>> getValueByGradeAndDepartment(Grade grade, Department department) {
            return values.stream()
                .filter(item -> item.getLeft().equals(grade) && item.getMiddle().equals(department))
                .collect(toList());
        }

        private void addConvertedValues(String grade, String department, String value) {
            final Grade filteredGrade = Stream.of(Grade.values())
                .filter(enumGrade -> enumGrade.getName().equals(grade))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
            final Department filteredDepartment = Stream.of(Department.values())
                .filter(enumDepartment -> enumDepartment.getName().equals(department))
                .findFirst()
                .orElseThrow(IllegalAccessError::new);
            values.add(of(filteredGrade, filteredDepartment, value));
        }
    }

    public static List<Triple<Grade, Department, String>> getAboutJob(Grade grade, Department department) {
        return filterAboutTheJob(grade, department);
    }

    public static List<Triple<Grade, Department, String>> getResponsibilities(Grade grade, Department department) {
        return filterResponsibilities(grade, department);
    }

    public static List<Triple<Grade, Department, String>> getRequirements(Grade grade, Department department) {
        return filterRequirements(grade, department);
    }

    public static List<Triple<Grade, Department, String>> getTechStack(Grade grade, Department department) {
        return filterTechnologyStack(grade, department);
    }

    private static List<Triple<Grade, Department, String>> filterAboutTheJob(Grade grade, Department department) {
        File file = new File("src/main/resources/jobfiles/AboutTheJob");
        return getFileContent(readSpecificFile(file)).getValueByGradeAndDepartment(grade, department);
    }

    private static List<Triple<Grade, Department, String>> filterResponsibilities(Grade grade, Department department) {
        File file = new File("src/main/resources/jobfiles/Responsibilities");
        return getFileContent(readSpecificFile(file)).getValueByGradeAndDepartment(grade, department);
    }

    private static List<Triple<Grade, Department, String>> filterRequirements(Grade grade, Department department) {
        File file = new File("src/main/resources/jobfiles/Requirements");
        return getFileContent(readSpecificFile(file)).getValueByGradeAndDepartment(grade, department);
    }

    private static List<Triple<Grade, Department, String>> filterTechnologyStack(Grade grade, Department department) {
        File file = new File("src/main/resources/jobfiles/TechnologiesStack");
        return getFileContent(readSpecificFile(file)).getValueByGradeAndDepartment(grade, department);
    }
}
