package com.humanresources.assistant.restclient.service;

import com.humanresources.assistant.backend.dto.FileDto;
import com.humanresources.assistant.restclient.CommonService;
import org.springframework.stereotype.Service;

@Service
public class FileRestService extends CommonService<FileDto> {

    public static final String FILE = "file";

    @Override
    public String getURI() {
        return FILE;
    }

    @Override
    public Class<FileDto> getResponseClass() {
        return FileDto.class;
    }
}
