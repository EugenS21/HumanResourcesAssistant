package com.humanresources.assistant.sshclients.model;

import com.humanresources.assistant.backend.dto.EmployeeDto;
import java.io.IOException;
import java.io.InputStream;
import lombok.Builder;
import lombok.Getter;
import lombok.SneakyThrows;
import net.schmizz.sshj.xfer.LocalFileFilter;
import net.schmizz.sshj.xfer.LocalSourceFile;

@Builder
@Getter
public class EmployeeFile implements LocalSourceFile {

    private final EmployeeDto employeeDto;
    private final InputStream inputStream;
    private final String fileName;

    @Override
    public String getName() {
        return fileName;
    }

    @Override
    @SneakyThrows
    public long getLength() {
        return inputStream.available();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return inputStream;
    }

    @Override
    public int getPermissions() throws IOException {
        return 0;
    }

    @Override
    public boolean isFile() {
        return false;
    }

    @Override
    public boolean isDirectory() {
        return false;
    }

    @Override
    public Iterable<? extends LocalSourceFile> getChildren(LocalFileFilter localFileFilter) throws IOException {
        return null;
    }

    @Override
    public boolean providesAtimeMtime() {
        return false;
    }

    @Override
    public long getLastAccessTime() throws IOException {
        return 0;
    }

    @Override
    public long getLastModifiedTime() throws IOException {
        return 0;
    }
}
