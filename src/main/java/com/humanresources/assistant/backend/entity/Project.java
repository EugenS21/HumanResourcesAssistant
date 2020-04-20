package com.humanresources.assistant.backend.entity;

import static com.humanresources.assistant.backend.entity.Project.TABLE_NAME;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity (name = TABLE_NAME)
public class Project {

    protected static final String TABLE_NAME = "t_project";

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    private String projectName;

    @OneToMany (mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Employee> employees;

    @OneToOne (mappedBy = "project")
    private Client client;
}
