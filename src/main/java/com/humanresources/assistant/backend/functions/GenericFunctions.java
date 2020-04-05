package com.humanresources.assistant.backend.functions;

import static java.util.stream.Collectors.joining;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

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
}
