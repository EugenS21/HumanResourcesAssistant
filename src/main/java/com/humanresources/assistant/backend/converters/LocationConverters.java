package com.humanresources.assistant.backend.converters;

import com.humanresources.assistant.backend.dto.LocationDto;
import com.humanresources.assistant.backend.entity.LocationEntity;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class LocationConverters extends Generic<LocationEntity, LocationDto> {

    public Function<List<LocationEntity>, List<LocationDto>> convertLocationEntityListToDto = convertListEntitiesToDto();
    public Function<List<LocationDto>, List<LocationEntity>> convertLocationDtoListToEntity = convertListDtoToEntities();
    public Function<LocationEntity, LocationDto> convertLocationEntityToDto = convertEntityToDto();
    public Function<LocationDto, LocationEntity> convertLocationDtoToEntity = convertDtoToEntity();

    @Override
    protected Function<LocationEntity, LocationDto> convertEntityToDto() {
        return location -> LocationDto.builder()
            .id(location.getId())
            .countryName(location.getCountryName())
            .city(location.getCity())
            .build();
    }

    @Override
    protected Function<LocationDto, LocationEntity> convertDtoToEntity() {
        return location -> LocationEntity.builder()
            .countryName(location.getCountryName())
            .city(location.getCity())
            .build();
    }

    @Override
    protected Function<List<LocationEntity>, List<LocationDto>> convertListEntitiesToDto() {
        return locations -> locations.stream()
            .map(location -> convertEntityToDto().apply(location))
            .collect(Collectors.toList());
    }

    @Override
    protected Function<List<LocationDto>, List<LocationEntity>> convertListDtoToEntities() {
        return locations -> locations.stream()
            .map(location -> convertDtoToEntity().apply(location))
            .collect(Collectors.toList());
    }

}
