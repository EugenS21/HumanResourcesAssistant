package com.humanresources.assistant.backend.entity;

import com.humanresources.assistant.backend.entity.authentication.User;
import java.time.LocalDate;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = Employee.TABLE_NAME)
@NoArgsConstructor
@AllArgsConstructor
@Setter
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
    @JoinColumn (name = "user_id", referencedColumnName = "id")
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

    @ManyToMany (fetch = FetchType.LAZY)
    @JoinTable (name = "t_employee_grade",
                joinColumns = @JoinColumn (name = "employee_id"),
                inverseJoinColumns = @JoinColumn (name = "grade_id"))
    private Set<Grade> grades;

}
