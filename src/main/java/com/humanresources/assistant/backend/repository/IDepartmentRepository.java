package com.humanresources.assistant.backend.repository;

import com.humanresources.assistant.backend.entity.Department;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDepartmentRepository extends JpaRepository<Department, Long> {

    List<Department> findAll();

}
