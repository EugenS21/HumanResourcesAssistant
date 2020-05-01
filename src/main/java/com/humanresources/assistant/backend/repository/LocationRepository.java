package com.humanresources.assistant.backend.repository;

import com.humanresources.assistant.backend.entity.LocationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends CrudRepository<LocationEntity, Integer> {

}
