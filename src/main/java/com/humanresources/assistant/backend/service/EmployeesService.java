package com.humanresources.assistant.backend.service;

import static com.google.common.collect.Lists.newArrayList;

import com.humanresources.assistant.backend.converters.EmployeeConverters;
import com.humanresources.assistant.backend.dto.EmployeeDto;
import com.humanresources.assistant.backend.entity.DepartmentEntity;
import com.humanresources.assistant.backend.entity.EmployeeEntity;
import com.humanresources.assistant.backend.entity.GradeEntity;
import com.humanresources.assistant.backend.entity.LocationEntity;
import com.humanresources.assistant.backend.entity.ProjectEntity;
import com.humanresources.assistant.backend.entity.authentication.UserEntity;
import com.humanresources.assistant.backend.exceptions.EmployeeNotFound;
import com.humanresources.assistant.backend.repository.EmployeeRepository;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeesService {

    @Autowired
    EmployeeConverters employeeConverters;

    @Autowired
    EmployeeRepository employeeRepository;

    @PersistenceContext
    EntityManager entityManager;

    public List<EmployeeDto> getAllEmployees() {
        return employeeConverters.convertEmployeeEntityListToDto.apply(newArrayList(employeeRepository.findAll()));
    }

    @Transactional
    public void saveUser(EmployeeDto employee) {
        EmployeeEntity employeeToSave = employeeConverters.convertEmployeeDtoToEntity.apply(employee);
        if (employee.getTeamLead() != null) {
            EmployeeEntity teamLead = entityManager.getReference(EmployeeEntity.class, employee.getTeamLead().getId());
            employeeToSave.setTeamLead(teamLead);
        }
        DepartmentEntity department = entityManager.getReference(DepartmentEntity.class, employee.getDepartment().getId());
        LocationEntity location = entityManager.getReference(LocationEntity.class, employee.getLocation().getId());
        ProjectEntity projectEntity = entityManager.getReference(ProjectEntity.class, employee.getProject().getId());
        GradeEntity grade = entityManager.getReference(GradeEntity.class, employee.getGrade().getId());
        UserEntity user = entityManager.getReference(UserEntity.class, employee.getUser().getId());
        employeeToSave.setDepartmentEntity(department);
        employeeToSave.setGradeEntity(grade);
        employeeToSave.setLocationEntity(location);
        employeeToSave.setProjectEntity(projectEntity);
        employeeToSave.setDepartmentEntity(department);
        employeeToSave.setUser(user);
        entityManager.persist(employeeToSave);
    }

    @SneakyThrows
    public EmployeeDto findById(Integer employeeId) {
        final Optional<EmployeeEntity> foundEmployee = employeeRepository.findById(employeeId);
        return employeeConverters.convertEmployeeEntityToDto.apply(foundEmployee.orElseThrow(EmployeeNotFound::new));
    }

    public void deleteEmployee(Integer employeeId) {
        employeeRepository.deleteById(employeeId);
    }

    public void deleteEmployee(EmployeeDto employeeDto) {
        employeeRepository.delete(employeeConverters.convertEmployeeDtoToEntity.apply(employeeDto));
    }
}
