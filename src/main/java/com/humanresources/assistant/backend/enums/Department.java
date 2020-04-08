package com.humanresources.assistant.backend.enums;

import lombok.Getter;

public enum Department {

    QA(1, "QA"),
    DEV(2, "Developer"),
    SP(3, "Support"),
    OPS(4, "Dev Ops"),
    ARH(5, "Architect");

    private Integer id;

    @Getter
    private String name;

    Department(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
