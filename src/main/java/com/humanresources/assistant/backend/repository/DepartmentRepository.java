package com.humanresources.assistant.backend.repository;

import com.humanresources.assistant.backend.entity.DepartmentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends CrudRepository<DepartmentEntity, Integer> {

}
