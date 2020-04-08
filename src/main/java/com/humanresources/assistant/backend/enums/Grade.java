package com.humanresources.assistant.backend.enums;

import lombok.Getter;

public enum Grade {

    J(1, "Junior"),
    T(2, "Technician"),
    S(3, "Senior"),
    E(4, "Engineer"),
    A(5, "Architect");

    private Integer id;

    @Getter
    private String name;

    Grade(Integer id, String name) {
        this.id = id;
        this.name = name;
    }


    @Override
    public String toString() {
        return this.getName();
    }
}
