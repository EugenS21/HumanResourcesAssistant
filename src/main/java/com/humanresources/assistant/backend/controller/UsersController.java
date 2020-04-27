package com.humanresources.assistant.backend.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.humanresources.assistant.backend.dto.UserDto;
import com.humanresources.assistant.backend.service.UsersService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {

    @Autowired
    UsersService userService;

    @GetMapping (value = "/user", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize ("hasAnyAuthority('admin','hr')")
    @ResponseBody
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok()
            .contentType(APPLICATION_JSON)
            .body(userService.getAllUsers());
    }

    @DeleteMapping (value = "/user/{id}", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize ("hasAuthority('admin')")
    @ResponseBody
    public ResponseEntity<?> deleteUser(@PathVariable (name = "id") Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok()
                .contentType(APPLICATION_JSON)
                .body(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity()
                .body("Something went wrong, please try again");
        }
    }

}
