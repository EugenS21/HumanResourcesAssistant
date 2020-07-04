package com.humanresources.assistant.backend.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.humanresources.assistant.backend.dto.EmployeeDto;
import com.humanresources.assistant.backend.dto.LeaveDto;
import com.humanresources.assistant.backend.dto.UserDto;
import com.humanresources.assistant.backend.security.jwt.JwtUtils;
import com.humanresources.assistant.backend.service.EmployeesService;
import com.humanresources.assistant.backend.service.LeaveService;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LeaveController {

    private final Logger logger = LoggerFactory.getLogger(LeaveController.class);

    @Autowired
    LeaveService leaveService;

    @Autowired
    EmployeesService employeesService;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping (value = "/leave", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize ("hasAnyAuthority('hr')")
    public ResponseEntity<List<LeaveDto>> getAllLeaves(@RequestHeader ("Authorization") String authorizationBearer) {
        UserDto userDetailsFromJwt = jwtUtils.getUserDetailsFromJwt(authorizationBearer);
        List<LeaveDto> allLeaves = leaveService.getAllLeaves().stream()
            .filter(leave -> !leave.getUser().getId().equals(userDetailsFromJwt.getId()))
            .collect(Collectors.toList());
        return ResponseEntity.ok().body(allLeaves);
    }

    @GetMapping (value = "/leave_by_id", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize ("hasAnyAuthority('hr', 'user', 'admin', 'hr assistant')")
    public ResponseEntity<List<LeaveDto>> getLeavesById(
        @RequestHeader ("Authorization") String authorizationBearer) {
        UserDto userDetailsFromJwt = jwtUtils.getUserDetailsFromJwt(authorizationBearer);
        return ResponseEntity.ok()
            .body(leaveService.getAllLeavesByUserId(userDetailsFromJwt.getId()));
    }

    @PostMapping (value = "/leave", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize ("hasAnyAuthority('hr', 'user', 'admin', 'hr assistant')")
    public ResponseEntity<LeaveDto> postNewLeave(
        @RequestHeader ("Authorization") String authorizationBearer,
        @Valid @RequestBody LeaveDto leave) {
        UserDto userDetailsFromJwt = jwtUtils.getUserDetailsFromJwt(authorizationBearer);
        EmployeeDto employee = employeesService.getEmployeeByUserId(userDetailsFromJwt.getId());
        leave.setUser(userDetailsFromJwt);
        leave.setCreatedBy(userDetailsFromJwt.getEmail());
        leave.setName(employee.getFirstName());
        leave.setSurName(employee.getSecondName());
        try {
            leaveService.saveLeave(leave);
            return ResponseEntity.ok().body(leave);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(leave);
        }
    }

    @PutMapping (value = "/leave/{id}", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize ("hasAnyAuthority('hr')")
    public ResponseEntity<LeaveDto> putLeave(
        @RequestHeader ("Authorization") String authorizationBearer,
        @PathVariable (name = "id") Long id,
        @Valid @RequestBody LeaveDto leave
    ) {
        UserDto userDetailsFromJwt = jwtUtils.getUserDetailsFromJwt(authorizationBearer);
        leave.setApprovedBy(userDetailsFromJwt.getUsername());
        leave.setApprovedDate(LocalDate.now());
        leaveService.updateLeave(id, leave);
        return ResponseEntity.ok().body(leave);
    }

    @DeleteMapping (value = "/leave/{id}")
    @PreAuthorize ("hasAnyAuthority('hr', 'user', 'admin', 'hr assistant')")
    public ResponseEntity<String> deleteLeave(@PathVariable (name = "id") @Valid Long id) {
        try {
            leaveService.deleteLeave(id);
            return ResponseEntity.ok().body("Leave was removed");
        } catch (Exception e) {
            logger.info("DELETE request could not be executed {}", e);
            return ResponseEntity.badRequest().body("Could not remove");
        }
    }

}
