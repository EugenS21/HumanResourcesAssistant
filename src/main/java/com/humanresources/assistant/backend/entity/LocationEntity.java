package com.humanresources.assistant.backend.entity;

import static com.humanresources.assistant.backend.entity.LocationEntity.TABLE_NAME;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Setter
@Getter
@Builder
public class LocationEntity {

    protected static final String TABLE_NAME = "t_location";

    @Id
    @Column
    @SequenceGenerator (name = "location_id_generator", sequenceName = "seq_location", allocationSize = 1)
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "location_id_generator")
    private Long id;

    @NotNull
    @Column
    private String countryName;

    @NotNull
    @Column
    private String city;

    @NotNull
    @Column
    private String street;

}
