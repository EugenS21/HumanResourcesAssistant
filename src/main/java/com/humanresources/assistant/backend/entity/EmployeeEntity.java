package com.humanresources.assistant.backend.entity;

import com.humanresources.assistant.backend.entity.authentication.UserEntity;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table (name = EmployeeEntity.TABLE_NAME)
@EntityListeners (AuditingEntityListener.class)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeEntity {

    protected static final String TABLE_NAME = "t_employee";

    @Id
    @Column
    @SequenceGenerator (name = "employee_id_generator", sequenceName = "seq_employee", allocationSize = 1)
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "employee_id_generator")
    private Integer id;

    @NotNull
    @Column
    private String firstName;

    @NotNull
    @Column
    private String secondName;

    @NotNull
    @Column
    private LocalDate birthDate;

    @NotNull
    @Column
    private LocalDate dateOfEmployment;

    @NotNull
    @Column
    private Integer salary;

    @NotNull
    @Column
    private Boolean isFired;

    @OneToOne
    @JoinColumn (name = "user_id")
    private UserEntity user;

    @OneToOne
    @JoinColumn (name = "team_lead_id")
    private EmployeeEntity teamLead;

    @OneToOne
    @JoinColumn (name = "department_id")
    private DepartmentEntity departmentEntity;

    @OneToOne
    @JoinColumn (name = "location_id")
    private LocationEntity locationEntity;

    @OneToOne
    @JoinColumn (name = "project_id")
    private ProjectEntity projectEntity;

    @OneToOne
    @JoinColumn (name = "grade_id")
    private GradeEntity gradeEntity;

}
