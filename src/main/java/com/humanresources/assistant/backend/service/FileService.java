package com.humanresources.assistant.backend.service;

import com.humanresources.assistant.backend.model.File;
import com.humanresources.assistant.backend.repository.IFileRepository;
import com.vaadin.flow.router.NotFoundException;
import java.io.IOException;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    @Autowired
    private IFileRepository iFileRepository;

    public File storeFile(MultipartFile multipartFile) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        File file;
        try {
            file = new File(fileName, multipartFile.getContentType(), multipartFile.getBytes());
            return iFileRepository.save(file);
        } catch (IOException ignored) {return null;}
    }

    public File getFile(String fileUuid) {
        return iFileRepository.findById(fileUuid).orElseThrow(NotFoundException::new);
    }
}
