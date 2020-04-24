package com.humanresources.assistant.backend.service;

import static com.google.common.collect.Lists.newArrayList;

import com.humanresources.assistant.backend.converters.DepartmentConverters;
import com.humanresources.assistant.backend.dto.DepartmentDto;
import com.humanresources.assistant.backend.entity.Department;
import com.humanresources.assistant.backend.exceptions.DepartmentNotFound;
import com.humanresources.assistant.backend.repository.DepartmentRepository;
import java.util.List;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    DepartmentConverters departmentConverters;

    public List<DepartmentDto> getAllDepartments() {
        return
            departmentConverters.convertDepartmentEntityListToDto.apply(newArrayList(departmentRepository.findAll()));
    }

    public void saveDepartment(DepartmentDto department) {
        departmentRepository.save(departmentConverters.convertDepartmentDtoToEntity.apply(department));
    }

    @SneakyThrows
    public DepartmentDto updateDepartment(Integer id, DepartmentDto departmentDto) {
        final Department foundDepartment = departmentRepository.findById(id).orElseThrow(DepartmentNotFound::new);
        foundDepartment.setName(departmentDto.getName());
        departmentRepository.save(foundDepartment);
        return departmentConverters.convertDepartmentEntityToDto.apply(foundDepartment);
    }

    public void deleteDepartment(Integer id) {
        departmentRepository.deleteById(id);
    }
}
