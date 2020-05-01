package com.humanresources.assistant.backend.enums;

import lombok.Getter;

public enum GradeEnum {

    J(1, "Junior"),
    T(2, "Technician"),
    S(3, "Senior"),
    E(4, "Engineer"),
    A(5, "Architect");

    private final Integer id;

    @Getter
    private final String name;

    GradeEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
