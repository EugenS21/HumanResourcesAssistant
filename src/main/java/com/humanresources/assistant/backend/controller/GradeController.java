package com.humanresources.assistant.backend.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.humanresources.assistant.backend.dto.GradeDto;
import com.humanresources.assistant.backend.service.GradeService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GradeController {

    private final Logger logger = LoggerFactory.getLogger(GradeController.class);

    @Autowired
    GradeService gradeService;

    @GetMapping (value = "/grade", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize ("hasAnyAuthority('hr','admin')")
    public ResponseEntity<List<GradeDto>> getAllGrades() {
        return ResponseEntity.ok()
            .body(gradeService.getAllGrades());
    }

    @PostMapping (value = "/grade", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize ("hasAuthority('admin')")
    public ResponseEntity<GradeDto> postNewGrade(@Valid @RequestBody GradeDto grade) {
        try {
            gradeService.saveGrade(grade);
            return ResponseEntity.ok().body(grade);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(grade);
        }
    }

    @DeleteMapping (value = "/grade/{id}")
    @PreAuthorize ("hasAuthority('admin')")
    public ResponseEntity<String> deleteGrade(@PathVariable (name = "id") @Valid Integer id) {
        try {
            gradeService.deleteGrade(id);
            return ResponseEntity.ok().body("Grade was removed");
        } catch (Exception e) {
            logger.info("DELETE request could not be executed {}", e);
            return ResponseEntity.badRequest().body("Could not remove");
        }
    }

}
