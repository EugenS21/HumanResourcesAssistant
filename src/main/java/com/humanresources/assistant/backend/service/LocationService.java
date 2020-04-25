package com.humanresources.assistant.backend.service;

import static com.google.common.collect.Lists.newArrayList;

import com.humanresources.assistant.backend.converters.LocationConverters;
import com.humanresources.assistant.backend.dto.LocationDto;
import com.humanresources.assistant.backend.entity.Location;
import com.humanresources.assistant.backend.exceptions.LocationNotFound;
import com.humanresources.assistant.backend.repository.LocationRepository;
import java.util.List;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    LocationConverters locationConverters;

    public List<LocationDto> getAllLocations() {
        return
            locationConverters.convertLocationEntityListToDto.apply(newArrayList(locationRepository.findAll()));
    }

    public void saveLocation(LocationDto location) {
        locationRepository.save(locationConverters.convertLocationDtoToEntity.apply(location));
    }

    @SneakyThrows
    public LocationDto updateLocation(Integer id, LocationDto locationDto) {
        final Location foundLocation = locationRepository.findById(id).orElseThrow(LocationNotFound::new);
        foundLocation.setCity(locationDto.getCity());
        foundLocation.setCountryName(locationDto.getCountryName());
        locationRepository.save(foundLocation);
        return locationConverters.convertLocationEntityToDto.apply(foundLocation);
    }

    public void deleteLocation(Integer id) {
        locationRepository.deleteById(id);
    }
}
