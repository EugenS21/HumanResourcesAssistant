package com.humanresources.assistant.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = User.TABLE_NAME)
@NoArgsConstructor
@AllArgsConstructor
public class User {
    protected static final String TABLE_NAME = "t_user";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "firstName")
    @NotNull
    private String firstName;

    @Column(name = "secondName")
    @NotNull
    private String secondName;

    @Column(name = "birthDate")
    @NotNull
    private Date birthDate;

    @Column(name = "dateOfEmployment")
    @NotNull
    private Date dateOfEmployment;

    @NotNull
    @Column(name = "grade")
    @Enumerated(EnumType.STRING)
    private Grade grade;

    @Column(name = "dateOfLastDegreeChange")
    private Date dateOfLastDegreeChange;

    @Column(name = "salary")
    @NotNull
    private Integer salary;

    @Column(name = "dayOfTheLastIncrease")
    private Date dayOfTheLastIncrease;

//    @Column(name = "department")
//    @Enumerated(EnumType.STRING)
//    private Department department;
//
//    @Column(name = "location")
//    @OneToOne
//    @PrimaryKeyJoinColumn(name = "id")
//    private Location locationId;

//    @Column(name = "currentProject")
//    @ManyToOne
//    @JoinColumn(name = "project_id")
//    private Project currentProjectId;

    @Column(name = "isFired")
    private Boolean isFired;
}
