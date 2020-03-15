package com.humanresources.assistant.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UploadFileResponse {

    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private Long fileSize;
}
