package com.humanresources.assistant.backend.entity;

import static com.humanresources.assistant.backend.entity.Project.TABLE_NAME;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
public class Project {

    protected static final String TABLE_NAME = "t_project";

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String projectName;

    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "client_id", referencedColumnName = "id", nullable = false)
    private Client client;
}
