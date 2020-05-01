package com.humanresources.assistant.backend.entity.authentication;

import static com.humanresources.assistant.backend.entity.authentication.RoleEntity.TABLE_NAME;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
public class RoleEntity {

    protected static final String TABLE_NAME = "t_role";

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

}
