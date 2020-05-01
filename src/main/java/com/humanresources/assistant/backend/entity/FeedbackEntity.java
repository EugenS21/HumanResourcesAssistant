package com.humanresources.assistant.backend.entity;

import static com.humanresources.assistant.backend.entity.FeedbackEntity.TABLE_NAME;

import javax.persistence.Column;
import javax.persistence.Entity;
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

@Entity
@Table (name = TABLE_NAME)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FeedbackEntity {

    protected static final String TABLE_NAME = "t_feedback";

    @Id
    @Column
    @SequenceGenerator (name = "feedback_id_generator", sequenceName = "seq_feedback", allocationSize = 1)
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "feedback_id_generator")
    private Integer id;

    @Column
    @NotNull
    private String description;

    @NotNull
    @OneToOne
    @JoinColumn (name = "from_employee_id")
    private EmployeeEntity fromEmployeeEntity;

    @NotNull
    @OneToOne
    @JoinColumn (name = "to_employee_id")
    private EmployeeEntity toEmployeeEntity;

}
