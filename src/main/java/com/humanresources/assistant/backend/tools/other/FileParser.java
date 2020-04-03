package com.humanresources.assistant.backend.tools.other;

import static com.humanresources.assistant.backend.tools.other.FileData.BENEFITS_FILE;
import static com.humanresources.assistant.backend.tools.other.FileData.COMPANY_DESCRIPTION_FILE;
import static com.humanresources.assistant.backend.tools.other.FileData.JOB_FILE;
import static com.humanresources.assistant.backend.tools.other.FileData.REQUIREMENTS_FILE;
import static com.humanresources.assistant.backend.tools.other.FileData.RESPONSIBILITIES_FILE;
import static com.humanresources.assistant.backend.tools.other.FileData.TECHNOLOGIES_FILE;
import static java.util.Collections.shuffle;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.tuple.Triple.of;

import com.humanresources.assistant.backend.enums.Department;
import com.humanresources.assistant.backend.enums.Grade;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.apache.commons.lang3.tuple.Triple;

public class FileParser {

    private static final int NR_OF_ELEMENTS = 5;

    private static List<String> getCompanyBenefits(List<String> readFile) {
        final List<String> filteredResults = readFile.stream()
            .limit(NR_OF_ELEMENTS)
            .collect(toList());
        shuffle(filteredResults);
        return filteredResults;
    }

    private static String getDescription(List<String> readFile) {
        return readFile.stream().collect(joining(" "));
    }

    private static LineToParse getGradeDepartmentDescriptionFile(List<String> lines) {
        LineToParse linesDetails = new LineToParse();
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

    private static LineToParse getDepartmentDescriptionFile(List<String> lines) {
        LineToParse linesDetails = new LineToParse();
        lines.forEach(line -> {
            Pattern departmentPattern = Pattern.compile("^.*(?=\\|)");
            Pattern valuePattern = Pattern.compile("(?<=\\|).*(?=;)");
            Matcher matcher = departmentPattern.matcher(line);
            String department = (matcher.find())
                                ? matcher.group(0)
                                : "";
            matcher = valuePattern.matcher(line);
            String description = (matcher.find())
                                 ? matcher.group(0)
                                 : "";
            linesDetails.addValues(null, department, description);
        });
        return linesDetails;
    }

    private static final class LineToParse {

        List<Triple<Grade, Department, String>> values;

        public LineToParse() {
            values = new ArrayList<>();
        }

        public void addValues(String grade, String department, String value) {
            addConvertedValues(grade, department, value);
        }

        public List<String> getValueByGradeAndDepartment(Grade grade, Department department) {
            final List<String> filteredResults = values.stream()
                .filter(item -> item.getLeft().equals(grade) && item.getMiddle().equals(department))
                .map(Triple::getRight)
                .limit(NR_OF_ELEMENTS)
                .collect(toList());
            shuffle(filteredResults);
            return filteredResults;
        }

        public List<String> getValueByDepartment(Department department) {
            final List<String> filteredResults = values.stream()
                .filter(item -> item.getMiddle().equals(department))
                .map(Triple::getRight)
                .limit(NR_OF_ELEMENTS)
                .collect(toList());
            shuffle(filteredResults);
            return filteredResults;
        }

        private void addConvertedValues(String grade, String department, String value) {
            Grade filteredGrade = null;
            if (grade != null) {
                filteredGrade = Stream.of(Grade.values())
                    .filter(enumGrade -> enumGrade.toString().equals(grade))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException(grade));
            }
            final Department filteredDepartment = Stream.of(Department.values())
                .filter(enumDepartment -> enumDepartment.toString().equals(department))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(department));
            values.add(of(filteredGrade, filteredDepartment, value));
        }
    }

    public static String getAboutJob(Grade grade, Department department) {
        return filterAboutTheJob(grade, department).get(0);
    }

    public static List<String> getResponsibilities(Grade grade, Department department) {
        return filterResponsibilities(grade, department);
    }

    public static List<String> getRequirements(Grade grade, Department department) {
        return filterRequirements(grade, department);
    }

    public static List<String> getTechStack(Department department) {
        return filterTechnologyStack(department);
    }

    public static List<String> getBenefits() {
        return getCompanyBenefits();
    }

    public static String getCompanyDescription() {
        return getCompanyInformation();
    }

    private static List<String> filterAboutTheJob(Grade grade, Department department) {
        return getGradeDepartmentDescriptionFile(JOB_FILE).getValueByGradeAndDepartment(grade, department);
    }

    private static List<String> filterResponsibilities(Grade grade, Department department) {
        return getGradeDepartmentDescriptionFile(RESPONSIBILITIES_FILE).getValueByGradeAndDepartment(grade, department);
    }

    private static List<String> filterRequirements(Grade grade, Department department) {
        return getGradeDepartmentDescriptionFile(REQUIREMENTS_FILE).getValueByGradeAndDepartment(grade, department);
    }

    private static List<String> filterTechnologyStack(Department department) {
        return getDepartmentDescriptionFile(TECHNOLOGIES_FILE).getValueByDepartment(department);
    }

    private static List<String> getCompanyBenefits() {
        return getCompanyBenefits(BENEFITS_FILE);
    }

    private static String getCompanyInformation() {
        return getDescription(COMPANY_DESCRIPTION_FILE);
    }
}
