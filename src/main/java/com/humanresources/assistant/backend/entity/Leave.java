package com.humanresources.assistant.backend.entity;

import static com.humanresources.assistant.backend.entity.Leave.TABLE_NAME;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table (name = TABLE_NAME)
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Leave {

    protected static final String TABLE_NAME = "t_leave";

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    private String name;

}
