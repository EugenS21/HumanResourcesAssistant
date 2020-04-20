package com.humanresources.assistant.backend.entity.authentication;

import static com.humanresources.assistant.backend.entity.authentication.Role.TABLE_NAME;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
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
public class Role {

    protected static final String TABLE_NAME = "t_role";

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

}
