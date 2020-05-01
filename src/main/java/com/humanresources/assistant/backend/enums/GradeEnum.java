package com.humanresources.assistant.backend.enums;

import com.humanresources.assistant.backend.exceptions.EnumNotFound;
import java.util.Arrays;
import lombok.Getter;
import lombok.SneakyThrows;

public enum GradeEnum {

    JUNIOR(1, "Junior"),
    TECHNICIAN(2, "Technician"),
    SENIOR(3, "Senior"),
    ENGINEER(4, "Engineer"),
    ARCHITECT(5, "Architect");

    private final Integer id;

    @Getter
    private final String name;

    GradeEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @SneakyThrows
    public static GradeEnum enumFrom(String enumValue) {
        return Arrays.stream(values())
            .filter(enumItem -> enumItem.getName().equals(enumValue))
            .findFirst()
            .orElseThrow(() -> new EnumNotFound(enumValue));
    }

    @Override
    public String toString() {
        return getName();
    }
}
