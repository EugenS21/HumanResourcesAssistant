package com.humanresources.assistant.backend.model;

public enum Department {

    J(1, "Junior"),
    T(2, "Technician"),
    S(3, "Senior"),
    E(4, "Engineer"),
    A(5, "Architect");

    private Integer id;

    private String name;

    Department(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
