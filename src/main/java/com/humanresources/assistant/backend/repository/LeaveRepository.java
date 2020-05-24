package com.humanresources.assistant.backend.repository;

import com.humanresources.assistant.backend.entity.LeaveEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveRepository extends CrudRepository<LeaveEntity, Integer> {

}
