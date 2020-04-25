package com.humanresources.assistant.backend.converters;

import com.humanresources.assistant.backend.dto.LocationDto;
import com.humanresources.assistant.backend.entity.Location;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class LocationConverters extends Generic<Location, LocationDto> {

    public Function<List<Location>, List<LocationDto>> convertLocationEntityListToDto = convertListEntitiesToDto();
    public Function<List<LocationDto>, List<Location>> convertLocationDtoListToEntity = convertListDtoToEntities();
    public Function<Location, LocationDto> convertLocationEntityToDto = convertEntityToDto();
    public Function<LocationDto, Location> convertLocationDtoToEntity = convertDtoToEntity();

    @Override
    protected Function<Location, LocationDto> convertEntityToDto() {
        return location -> LocationDto.builder()
            .id(location.getId())
            .countryName(location.getCountryName())
            .city(location.getCity())
            .build();
    }

    @Override
    protected Function<LocationDto, Location> convertDtoToEntity() {
        return location -> Location.builder()
            .countryName(location.getCountryName())
            .city(location.getCity())
            .build();
    }

    @Override
    protected Function<List<Location>, List<LocationDto>> convertListEntitiesToDto() {
        return locations -> locations.stream()
            .map(location -> convertEntityToDto().apply(location))
            .collect(Collectors.toList());
    }

    @Override
    protected Function<List<LocationDto>, List<Location>> convertListDtoToEntities() {
        return locations -> locations.stream()
            .map(location -> convertDtoToEntity().apply(location))
            .collect(Collectors.toList());
    }

}
