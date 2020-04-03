package com.humanresources.assistant.backend.functions;

import java.util.List;
import java.util.function.Function;

public class GenericFunctions {

    public static Function<List<String>, String> getStringFromList =
        list -> String.join("\n", list);
}
