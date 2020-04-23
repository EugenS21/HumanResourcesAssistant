package com.humanresources.assistant.backend.repository;

import com.humanresources.assistant.backend.entity.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, String> {

}
