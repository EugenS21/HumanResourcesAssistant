package com.humanresources.assistant.backend.service;

import com.humanresources.assistant.backend.entity.Department;
import com.humanresources.assistant.backend.repository.IDepartmentRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {

    @Autowired
    IDepartmentRepository departmentRepository;

    public List<Department> getAllDepartments() {
        return new ArrayList<>(departmentRepository.findAll());
    }

    public void saveDepartment(Department department) {
        departmentRepository.save(department);
    }
}
