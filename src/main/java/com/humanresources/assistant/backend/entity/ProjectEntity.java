package com.humanresources.assistant.backend.entity;

import static com.humanresources.assistant.backend.entity.ProjectEntity.TABLE_NAME;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity (name = TABLE_NAME)
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectEntity {

    protected static final String TABLE_NAME = "t_project";

    @Id
    @Column
    @SequenceGenerator (name = "project_id_generator", sequenceName = "seq_project", allocationSize = 10)
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "project_id_generator")
    private Long id;

    @NotNull
    @Column
    private String projectName;

    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "client_id", referencedColumnName = "id", nullable = false)
    private ClientEntity clientEntity;
}
