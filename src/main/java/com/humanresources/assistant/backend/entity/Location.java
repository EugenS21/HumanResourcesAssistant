package com.humanresources.assistant.backend.entity;

import static com.humanresources.assistant.backend.entity.Location.TABLE_NAME;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
public class Location {

    protected static final String TABLE_NAME = "t_location";

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    private String countryName;

    @NotNull
    private String city;

    @OneToMany (mappedBy = "location", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Employee> employees;

}