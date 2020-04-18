package com.humanresources.assistant.backend.model;

import com.humanresources.assistant.backend.enums.Grade;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = Employee.TABLE_NAME)
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Employee {

    protected static final String TABLE_NAME = "t_employee";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull
    @Column(name = "firstName")
    private String firstName;

    @NotNull
    @Column(name = "secondName")
    private String secondName;

    @Column(name = "birthDate")
    @NotNull
    private LocalDate birthDate;

    @Column(name = "dateOfEmployment")
    @NotNull
    private LocalDate dateOfEmployment;

    @NotNull
    @Column(name = "grade")
    @Enumerated(EnumType.STRING)
    private Grade grade;

//    @Column(name = "dateOfLastDegreeChange")
//    private Date dateOfLastDegreeChange;
//
//    @Column(name = "salary")
//    @NotNull
//    private Integer salary;
//
//    @Column(name = "dayOfTheLastIncrease")
//    private Date dayOfTheLastIncrease;
//
//    @Column(name = "department")
//    @Enumerated(EnumType.STRING)
//    private Department department;

//    @Column(name = "location")
//    @OneToOne
//    @PrimaryKeyJoinColumn(name = "id")
//    private Location locationId;
//
//    @Column(name = "currentProject")
//    @ManyToOne
//    @JoinColumn(name = "project_id")
//    private Project currentProjectId;

    @Column(name = "isFired")
    private Boolean isFired;
}
