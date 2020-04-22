package com.humanresources.assistant.backend.functions;

import static java.util.stream.Collectors.joining;

import com.humanresources.assistant.restclient.model.Department;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GenericFunctions {

    public static Function<List<String>, String> getStringFromList =
        list -> list.stream()
            .map(String::trim)
            .filter(item -> !item.isEmpty())
            .map(item -> item.replaceAll("^", "- "))
            .map(item -> item.replaceAll("(;$)", ""))
            .map(item -> item.replaceAll("$", ";"))
            .collect(joining("\n"));

    public static Function<String, List<String>> getListFromString =
        string -> Arrays.asList(string.replaceAll("- |;", "").split("\\n"));

    public static Function<List<Department>, List<com.humanresources.assistant.backend.entity.Department>>
        convertDepartmentModelToEntity =
        departments ->
            departments.stream()
                .map(
                    department -> com.humanresources.assistant.backend.entity.Department.builder()
                        .id(department.getId())
                        .name(department.getName())
                        .employees(Optional.ofNullable(department.getEmployees()).orElse(null))
                        .build())
                .collect(Collectors.toList());
}
