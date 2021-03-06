package com.humanresources.assistant.backend.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.google.common.net.HttpHeaders;
import com.humanresources.assistant.backend.dto.FileDto;
import com.humanresources.assistant.backend.dto.UserDto;
import com.humanresources.assistant.backend.security.jwt.JwtUtils;
import com.humanresources.assistant.backend.service.FileService;
import java.io.FileNotFoundException;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {

    @Autowired
    private Logger log;

    @Autowired
    private FileService fileService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping (value = "/file")
    @PreAuthorize ("hasAnyAuthority('user','hr')")
    public ResponseEntity<FileDto> postFile(@RequestParam ("file") MultipartFile file,
        @RequestHeader ("Authorization") String userDetails,
        @Valid @NotNull @ModelAttribute FileDto fileToUpload) {
        UserDto userData = jwtUtils.getUserDetailsFromJwt(userDetails);
        fileToUpload.setUser(userData);
        try {
            fileService.saveFile(fileToUpload, file);
            return ResponseEntity.ok().body(fileToUpload);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(fileToUpload);
        }
    }

    @GetMapping (value = "/file", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize ("hasAnyAuthority('hr','user')")
    public ResponseEntity<List<FileDto>> getAllFiles() {
        return ResponseEntity.ok(fileService.getFiles());
    }

    @GetMapping (value = "/file/{id}")
    @PreAuthorize ("hasAnyAuthority('hr','user')")
    @ResponseBody
    public ResponseEntity<Resource> getFileById(@PathVariable (name = "id") @Valid Integer id) {
        FileDto foundFile;
        try {
            foundFile = fileService.getFiles().stream()
                .filter(file -> file.getId().equals((long) id))
                .findFirst()
                .orElseThrow(FileNotFoundException::new);
        } catch (FileNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType("application/octet-stream"))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + foundFile.getFileName() + "\"")
            .body(fileService.getFileById(foundFile));
    }

    @DeleteMapping (value = "/file/{id}")
    @PreAuthorize ("hasAuthority('user')")
    public ResponseEntity<String> deleteFile(@PathVariable (name = "id") @Valid Integer id) {
        try {
            fileService.deleteFile(id);
            return ResponseEntity.ok().body("File was removed");
        } catch (Exception e) {
            log.error("Could not remove file " + id);
            return ResponseEntity.badRequest().body("Could not remove");
        }
    }
}
