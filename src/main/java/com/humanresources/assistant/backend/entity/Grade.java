package com.humanresources.assistant.backend.entity;

import static com.humanresources.assistant.backend.entity.Grade.TABLE_NAME;

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
@Getter
@Setter
public class Grade {

    protected static final String TABLE_NAME = "t_grade";

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    private String name;

}
