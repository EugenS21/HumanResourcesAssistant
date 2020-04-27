package com.humanresources.assistant.backend.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import com.humanresources.assistant.backend.dto.EmployeeDto;
import com.humanresources.assistant.backend.service.EmployeesService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeesController {

    @Autowired
    EmployeesService employeesService;

    @RequestMapping (method = GET, value = "/employee", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize ("hasAuthority('hr')")
    @ResponseBody
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        return ResponseEntity.ok()
            .contentType(APPLICATION_JSON)
            .body(employeesService.getAllEmployees());
    }

    @RequestMapping (method = POST, value = "/employee", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize ("hasAuthority('hr')")
    public void postNewEmployee(@RequestBody EmployeeDto employee) {
        employeesService.saveUser(employee);
    }

    @RequestMapping (method = GET, value = "/employee/{id}", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize ("hasAuthority('hr')")
    @ResponseBody
    public ResponseEntity<EmployeeDto> getEmployee(@RequestParam ("id") @PathVariable ("id") String employeeId) {
        return ResponseEntity.ok()
            .contentType(APPLICATION_JSON)
            .body(employeesService.findById(employeeId));
    }

    @RequestMapping (method = DELETE, value = "/employee", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize ("hasAuthority('hr')")
    public void deleteEmployee(@RequestParam ("id") String employeeId) {
        employeesService.deleteEmployee(employeeId);
    }

    @RequestMapping (method = PUT, value = "/employee", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize ("hasAuthority('hr')")
    public void deleteEmployee(@RequestParam ("id") String employeeId, EmployeeDto employeeDto) {
        employeesService.deleteEmployee(employeeId);
        employeesService.saveUser(employeeDto);
    }
}
