package com.humanresources.assistant.backend.entity;

import static com.humanresources.assistant.backend.entity.LeaveEntity.TABLE_NAME;

import com.humanresources.assistant.backend.enums.LeaveTypeEnum;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table (name = TABLE_NAME)
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class LeaveEntity {

    protected static final String TABLE_NAME = "t_leave";

    @Id
    @Column
    @SequenceGenerator (name = "leave_generator", sequenceName = "seq_leave", allocationSize = 10)
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "leave_generator")
    private Long id;

    @Column
    @NotNull
    @Enumerated (EnumType.STRING)
    private LeaveTypeEnum name;

    @Column
    @CreatedDate
    private LocalDate creationDate;

    @Column
    @CreatedBy
    private String createdBy;

    @Column
    @LastModifiedDate
    private LocalDate approvedDate;

    @Column
    @LastModifiedBy
    private String approvedBy;

    @Column
    private Boolean isApproved;

    @ManyToMany (fetch = FetchType.LAZY)
    @JoinTable (name = "t_employee_leaves",
                joinColumns = @JoinColumn (name = "employee_id"),
                inverseJoinColumns = @JoinColumn (name = "leave_id"))
    private List<LeaveEntity> leaves;

}
