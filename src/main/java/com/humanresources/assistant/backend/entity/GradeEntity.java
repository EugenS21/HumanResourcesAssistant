package com.humanresources.assistant.backend.entity;

import static com.humanresources.assistant.backend.entity.GradeEntity.TABLE_NAME;

import com.humanresources.assistant.backend.enums.GradeEnum;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

@Entity
@Table (name = TABLE_NAME)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Immutable
public class GradeEntity {

    protected static final String TABLE_NAME = "t_grade";

    @Id
    @Column
    @SequenceGenerator (name = "grade_id_generator", sequenceName = "seq_grade", allocationSize = 1)
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "grade_id_generator")
    private Integer id;

    @Column
    @NotNull
    @Enumerated (EnumType.STRING)
    private GradeEnum grade;

    @Column
    @NotNull
    private Double additionToSalary;

    @OneToOne (mappedBy = "gradeEntity")
    private EmployeeEntity employee;
}
