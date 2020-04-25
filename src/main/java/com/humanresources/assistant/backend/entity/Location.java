package com.humanresources.assistant.backend.entity;

import static com.humanresources.assistant.backend.entity.Location.TABLE_NAME;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
public class Location {

    protected static final String TABLE_NAME = "t_location";

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String countryName;

    @NotNull
    private String city;
}
