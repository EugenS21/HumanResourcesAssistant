package com.humanresources.assistant.restclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.humanresources.assistant.backend.entity.Employee;
import java.util.Set;
import lombok.Getter;

@Getter
public class Department {

    @JsonProperty ("id")
    private Integer id;

    @JsonProperty ("name")
    private String name;

    @JsonProperty ("employees")
    private Set<Employee> employees;
}
