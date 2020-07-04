package com.humanresources.assistant.backend.entity;

import static com.humanresources.assistant.backend.entity.LeaveEntity.TABLE_NAME;

import com.humanresources.assistant.backend.entity.authentication.UserEntity;
import com.humanresources.assistant.backend.enums.LeaveTypeEnum;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table (name = TABLE_NAME)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class LeaveEntity {

    protected static final String TABLE_NAME = "t_leave";

    @Id
    @Column
    @SequenceGenerator (name = "leave_generator", sequenceName = "seq_leave", allocationSize = 1)
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "leave_generator")
    private Long id;

    @Column
    @NotNull
    @Enumerated (EnumType.STRING)
    private LeaveTypeEnum leaveType;

    @Column
    @CreatedDate
    private LocalDate creationDate;

    @Column
    @NotNull
    private LocalDate startDate;

    @Column
    @NotNull
    private LocalDate endDate;

    @Column
    private String description;

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

    @Column
    private String firstName;

    @Column
    private String lastName;

    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;

}
