package com.humanresources.assistant.backend.entity;

import static com.humanresources.assistant.backend.entity.DepartmentEntity.TABLE_NAME;

import com.humanresources.assistant.backend.enums.DepartmentEnum;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import org.hibernate.annotations.Immutable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table (name = TABLE_NAME)
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners (AuditingEntityListener.class)
@Builder
@Getter
@Setter
@Immutable
public class DepartmentEntity {

    protected final static String TABLE_NAME = "t_department";

    @Id
    @Column
    @SequenceGenerator (name = "department_id_generator", sequenceName = "seq_department", allocationSize = 1)
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "department_id_generator")
    private Integer id;

    @NotNull
    @Column
    @Enumerated (EnumType.STRING)
    private DepartmentEnum department;

    @OneToOne
    @JoinColumn (name = "manager_id")
    private EmployeeEntity managerId;

    @Column
    @NotNull
    private Integer startingSalary;
}
