package com.humanresources.assistant.backend.converters;

import com.humanresources.assistant.backend.dto.FileDto;
import com.humanresources.assistant.backend.entity.FileEntity;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FileConverters extends Generic<FileEntity, FileDto> {

    @Autowired
    private UserConverters userConverters;

    public Function<List<FileEntity>, List<FileDto>> convertFileEntityListToDto = convertListEntitiesToDto();
    public Function<List<FileDto>, List<FileEntity>> convertFileDtoListToEntity = convertListDtoToEntities();
    public Function<FileEntity, FileDto> convertFileEntityToDto = convertEntityToDto();
    public Function<FileDto, FileEntity> convertFileDtoToEntity = convertDtoToEntity();

    @Override
    protected Function<FileEntity, FileDto> convertEntityToDto() {
        return file -> FileDto.builder()
            .id(file.getId())
            .fileName(file.getFileName())
            .fileType(file.getFileType())
            .department(file.getDepartment())
            .description(file.getDescription())
            .phoneNumber(file.getPhoneNumber())
            .user(userConverters.convertEntityToDto().apply(file.getUser()))
            .build();
    }

    @Override
    protected Function<FileDto, FileEntity> convertDtoToEntity() {
        return file -> FileEntity.builder()
            .fileName(file.getFileName())
            .fileType(file.getFileType())
            .department(file.getDepartment())
            .description(file.getDescription())
            .phoneNumber(file.getPhoneNumber())
            .user(userConverters.convertDtoToEntity().apply(file.getUser()))
            .build();
    }

    @Override
    protected Function<List<FileEntity>, List<FileDto>> convertListEntitiesToDto() {
        return files -> files.stream()
            .map(File -> convertEntityToDto().apply(File))
            .collect(Collectors.toList());
    }

    @Override
    protected Function<List<FileDto>, List<FileEntity>> convertListDtoToEntities() {
        return files -> files.stream()
            .map(File -> convertDtoToEntity().apply(File))
            .collect(Collectors.toList());
    }

}
