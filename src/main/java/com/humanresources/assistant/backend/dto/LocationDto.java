package com.humanresources.assistant.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
@Getter
public class LocationDto {

    @JsonProperty ("id")
    private Long id;

    @JsonProperty ("countryName")
    private String countryName;

    @JsonProperty ("city")
    private String city;

}
