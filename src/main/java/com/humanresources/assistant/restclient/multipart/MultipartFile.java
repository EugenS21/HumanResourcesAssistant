package com.humanresources.assistant.restclient.multipart;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MultipartFile implements org.springframework.web.multipart.MultipartFile {

    private final ByteArrayOutputStream byteStream;
    private final String fileName;
    private final String contentType;

    @Override
    public String getName() {
        return fileName;
    }

    @Override
    public String getOriginalFilename() {
        return fileName;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public boolean isEmpty() {
        return byteStream.toByteArray().length == 0;
    }

    @Override
    public long getSize() {
        return byteStream.toByteArray().length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return byteStream.toByteArray();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(byteStream.toByteArray());
    }

    @Override
    public void transferTo(File file) throws IOException, IllegalStateException {
        new FileOutputStream(file).write(byteStream.toByteArray());
    }
}
