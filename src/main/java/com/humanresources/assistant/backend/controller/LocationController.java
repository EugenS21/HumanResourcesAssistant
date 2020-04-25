package com.humanresources.assistant.backend.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.humanresources.assistant.backend.dto.LocationDto;
import com.humanresources.assistant.backend.service.LocationService;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LocationController {

    private final Logger logger = LoggerFactory.getLogger(LocationController.class);

    @Autowired
    LocationService locationService;

    @GetMapping (value = "/location", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize ("hasAnyAuthority('hr','admin')")
    public ResponseEntity<List<LocationDto>> getAllLocations() {
        return ResponseEntity.ok()
            .body(locationService.getAllLocations());
    }

    @PostMapping (value = "/location", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize ("hasAuthority('admin')")
    public ResponseEntity<LocationDto> postNewLocation(@Valid @RequestBody LocationDto location) {
        try {
            locationService.saveLocation(location);
            return ResponseEntity.ok().body(location);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(location);
        }
    }

    @DeleteMapping (value = "/location/{id}")
    @PreAuthorize ("hasAuthority('admin')")
    public ResponseEntity<String> deleteLocation(@PathVariable (name = "id") @Valid Integer id) {
        try {
            locationService.deleteLocation(id);
            return ResponseEntity.ok().body("Location was removed");
        } catch (Exception e) {
            logger.info("DELETE request could not be executed {}", e);
            return ResponseEntity.badRequest().body("Could not remove");
        }
    }

}
