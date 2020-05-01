package com.humanresources.assistant.backend.entity;

import static com.humanresources.assistant.backend.entity.RequestEntity.TABLE_NAME;

import com.humanresources.assistant.backend.enums.RequestTypeEnum;
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
@Getter
@Setter
public class RequestEntity {

    protected static final String TABLE_NAME = "t_request";

    @Id
    @Column
    @SequenceGenerator (name = "request_generator", sequenceName = "seq_request", allocationSize = 10)
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "request_generator")
    private Integer id;

    @NotNull
    @Column
    @Enumerated (EnumType.STRING)
    private RequestTypeEnum request;

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
    @JoinTable (name = "t_employee_requests",
                joinColumns = @JoinColumn (name = "employee_id"),
                inverseJoinColumns = @JoinColumn (name = "request_id"))
    private List<EmployeeEntity> employeeEntities;

}
