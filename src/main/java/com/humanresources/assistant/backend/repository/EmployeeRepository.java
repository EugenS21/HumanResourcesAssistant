package com.humanresources.assistant.backend.repository;

import com.humanresources.assistant.backend.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends CrudRepository<EmployeeEntity, Integer> {

    @Query ("SELECT u from EmployeeEntity u where u.user.id=?1")
    EmployeeEntity findEmployeeEntityByUserId(Long userId);

}
