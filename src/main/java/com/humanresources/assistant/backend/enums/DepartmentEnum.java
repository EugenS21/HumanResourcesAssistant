package com.humanresources.assistant.backend.enums;

import com.humanresources.assistant.backend.exceptions.EnumNotFound;
import java.util.Arrays;
import lombok.Getter;
import lombok.SneakyThrows;

public enum DepartmentEnum {

    QA(1, "QA"),
    DEVELOPER(2, "Developer"),
    SUPPORT(3, "Support"),
    DEV_OPS(4, "Dev Ops"),
    ARCHITECTURE(5, "Architecture");

    private final Integer id;

    @Getter
    private final String name;

    DepartmentEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @SneakyThrows
    public static DepartmentEnum enumFrom(String enumValue) {
        return Arrays.stream(values())
            .filter(enumItem -> enumItem.name.equals(enumValue))
            .findFirst()
            .orElseThrow(() -> new EnumNotFound(enumValue));
    }
}
