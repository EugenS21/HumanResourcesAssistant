package com.humanresources.assistant.backend.entity;

import static com.humanresources.assistant.backend.entity.Grade.TABLE_NAME;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table (name = TABLE_NAME)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Request {

    protected static final String TABLE_NAME = "t_request";

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String name;

    @ManyToMany (fetch = FetchType.LAZY)
    @JoinTable (name = "t_employee_request",
                joinColumns = @JoinColumn (name = "request_id"),
                inverseJoinColumns = @JoinColumn (name = "employee_id"))
    private Set<Employee> employees;

}
