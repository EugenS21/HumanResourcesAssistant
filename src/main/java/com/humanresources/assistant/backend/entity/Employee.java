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
import javax.persistence.ManyToOne;
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

    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn (name = "user_id", unique = true, nullable = false,
                 updatable = false, referencedColumnName = "id")
    private User user;

    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "department_id", nullable = false)
    private Department department;

    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "location_id", nullable = false)
    private Location location;

    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "project_id", nullable = false)
    private Project project;

    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn (name = "grade_id", unique = true, nullable = false,
                 updatable = false, referencedColumnName = "id")
    private Grade grades;

}
