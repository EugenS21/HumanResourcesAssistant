package com.humanresources.assistant.backend.service;

import com.humanresources.assistant.backend.converters.FileConverters;
import com.humanresources.assistant.backend.dto.FileDto;
import com.humanresources.assistant.backend.exceptions.FileNotFound;
import com.humanresources.assistant.backend.repository.FileRepository;
import com.humanresources.assistant.sshclients.clients.SftpClient;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileConverters fileConverters;

    @Autowired
    private SftpClient sftpClient;

    @Value ("${java.io.tmpdir}")
    private String tmpDir;

    public List<FileDto> getFiles() {
        return fileConverters.convertFileEntityListToDto.apply(fileRepository.findAll());
    }

    @SneakyThrows
    public FileDto getFileById(Integer id) {
        return fileConverters.convertFileEntityToDto.apply(fileRepository.findById(id).orElseThrow(FileNotFound::new));
    }

    @SneakyThrows
    public void saveFile(FileDto fileDetails, MultipartFile file) {
        Path filePath = Paths.get(tmpDir, file.getOriginalFilename());
        file.transferTo(filePath);
        sftpClient.uploadUserFile(fileDetails, filePath.toString());
        fileRepository.save(fileConverters.convertFileDtoToEntity.apply(fileDetails));
    }

    public void deleteFile(Integer fileToRemove) {
        fileRepository.deleteById(fileToRemove);
    }

}
