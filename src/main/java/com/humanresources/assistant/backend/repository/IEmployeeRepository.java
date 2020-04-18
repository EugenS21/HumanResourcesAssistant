package com.humanresources.assistant.backend.repository;

import com.humanresources.assistant.backend.model.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmployeeRepository extends CrudRepository<Employee, String> {

}
