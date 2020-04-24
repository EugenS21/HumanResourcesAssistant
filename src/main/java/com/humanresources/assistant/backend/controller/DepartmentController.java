package com.humanresources.assistant.backend.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.humanresources.assistant.backend.dto.DepartmentDto;
import com.humanresources.assistant.backend.service.DepartmentService;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DepartmentController {

    private final Logger logger = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    DepartmentService departmentService;

    @GetMapping (value = "/department", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize ("hasAnyAuthority('hr','admin')")
    public ResponseEntity<List<DepartmentDto>> getAllDepartments() {
        return ResponseEntity.ok()
            .body(departmentService.getAllDepartments());
    }

    @PostMapping (value = "/department", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize ("hasAuthority('admin')")
    public ResponseEntity<DepartmentDto> postNewDepartment(@Valid @RequestBody DepartmentDto department) {
        try {
            departmentService.saveDepartment(department);
            return ResponseEntity.ok().body(department);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(department);
        }
    }

    @PutMapping (value = "/department/{id}", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize ("hasAuthority('admin')")
    public ResponseEntity<DepartmentDto> putDepartment(@PathVariable (name = "id") Integer id,
        @Valid @RequestBody DepartmentDto departmentDto) {
        try {
            departmentService.updateDepartment(id, departmentDto);
            return ResponseEntity.ok().body(departmentDto);
        } catch (Exception e) {
            logger.error("PUT request could not be executed {}", e);
            return ResponseEntity.badRequest().body(departmentDto);
        }
    }

    @DeleteMapping (value = "/department/{id}")
    @PreAuthorize ("hasAuthority('admin')")
    public ResponseEntity<String> deleteDepartment(@PathVariable (name = "id") @Valid Integer id) {
        try {
            departmentService.deleteDepartment(id);
            return ResponseEntity.ok().body("Department was removed");
        } catch (Exception e) {
            logger.info("DELETE request could not be executed {}", e);
            return ResponseEntity.badRequest().body("Could not remove");
        }
    }

}
