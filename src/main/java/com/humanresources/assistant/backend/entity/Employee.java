package com.humanresources.assistant.backend.entity;

import com.humanresources.assistant.backend.entity.authentication.User;
import java.time.LocalDate;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = Employee.TABLE_NAME)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Employee {

    protected static final String TABLE_NAME = "t_employee";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String firstName;

    @NotNull
    private String secondName;

    @NotNull
    private LocalDate birthDate;

    @NotNull
    private LocalDate dateOfEmployment;

    @NotNull
    private Integer salary;

    @NotNull
    private Boolean isFired;

    @OneToOne (fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn (name = "user_id", unique = true, nullable = false,
                 updatable = false, referencedColumnName = "id")
    private User user;

    @OneToOne (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn (name = "department_id")
    private Department department;

    @OneToOne (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn (name = "location_id")
    private Location location;

    @OneToOne (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn (name = "project_id")
    private Project project;

    @OneToOne (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn (name = "grade_id")
    private Grade grades;

}
