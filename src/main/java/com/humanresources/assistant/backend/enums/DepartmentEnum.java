package com.humanresources.assistant.backend.enums;

import lombok.Getter;

public enum DepartmentEnum {

    QA(1, "QA"),
    DEV(2, "Developer"),
    SP(3, "Support"),
    OPS(4, "Dev Ops"),
    ARH(5, "Architect");

    private final Integer id;

    @Getter
    private final String name;

    DepartmentEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
