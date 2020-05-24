package com.humanresources.assistant.backend.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.humanresources.assistant.backend.dto.LeaveDto;
import com.humanresources.assistant.backend.dto.UserDto;
import com.humanresources.assistant.backend.entity.LeaveEntity;
import com.humanresources.assistant.backend.security.jwt.JwtUtils;
import com.humanresources.assistant.backend.service.LeaveService;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
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

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping (value = "/leave", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize ("hasAnyAuthority('hr', 'user', 'admin', 'hr assistant')")
    public ResponseEntity<List<LeaveDto>> getAllLeaves() {
        return ResponseEntity.ok()
            .body(leaveService.getAllLeaves());
    }

    @PostMapping (value = "/leave", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize ("hasAnyAuthority('hr', 'user', 'admin', 'hr assistant')")
    public ResponseEntity<LeaveDto> postNewLeave(
        @RequestHeader ("Authorization") String authorizationBearer,
        @Valid @RequestBody LeaveDto leave) {
        UserDto userDetailsFromJwt = jwtUtils.getUserDetailsFromJwt(authorizationBearer);
        leave.setUser(userDetailsFromJwt);
        try {
            leaveService.saveLeave(leave);
            return ResponseEntity.ok().body(leave);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(leave);
        }
    }

    @PutMapping (value = "/leave", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize ("hasAnyAuthority('hr', 'user', 'admin', 'hr assistant')")
    @Transactional
    public ResponseEntity<LeaveDto> putClient(@PathVariable (name = "id") Integer id,
        @Valid @RequestBody LeaveDto leave) {
        try {
            entityManager.merge(entityManager.getReference(LeaveEntity.class, id));
            return ResponseEntity.ok().body(leave);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(leave);
        }
    }

    @DeleteMapping (value = "/leave/{id}")
    @PreAuthorize ("hasAnyAuthority('hr', 'user', 'admin', 'hr assistant')")
    public ResponseEntity<String> deleteLeave(@PathVariable (name = "id") @Valid Integer id) {
        try {
            leaveService.deleteLeave(id);
            return ResponseEntity.ok().body("Leave was removed");
        } catch (Exception e) {
            logger.info("DELETE request could not be executed {}", e);
            return ResponseEntity.badRequest().body("Could not remove");
        }
    }

}
