package com.humanresources.assistant.backend.controller;

import com.humanresources.assistant.backend.entity.File;
import com.humanresources.assistant.backend.model.UploadFileResponse;
import com.humanresources.assistant.backend.service.FileService;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(
        @RequestParam("file")
            MultipartFile fileToUpload) {
        File fileEntity = fileService.storeFile(fileToUpload);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/downloadFile/")
            .path(String.valueOf(fileEntity.getUuid()))
            .toUriString();

        return new UploadFileResponse(
            fileEntity.getFileName(),
            fileDownloadUri,
            fileToUpload.getContentType(),
            fileToUpload.getSize());
    }

    @PostMapping("/uploadFiles")
    public List<UploadFileResponse> uploadFiles(
        @RequestParam("files")
            MultipartFile[] filesToUpload) {

        return Arrays.stream(filesToUpload)
            .map(file -> uploadFile(file))
            .collect(Collectors.toList());
    }

    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(
        @PathVariable
            String fileUuid) {
        File fileEntity = fileService.getFile(fileUuid);
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(fileEntity.getFileType()))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileEntity.getFileName() + "\"")
            .body(new ByteArrayResource(fileEntity.getData()));
    }
}
