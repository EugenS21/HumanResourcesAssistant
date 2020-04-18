package com.humanresources.assistant.backend.model.authentication;

import com.humanresources.assistant.backend.enums.ERole;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table (name = "t_role")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue
    private Integer id;

    @Enumerated (EnumType.STRING)
    @Column (length = 20)
    private ERole name;

}
