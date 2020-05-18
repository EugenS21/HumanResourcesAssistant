package com.humanresources.assistant.sshclients.clients;

import static java.lang.String.format;

import com.humanresources.assistant.backend.dto.FileDto;
import com.humanresources.assistant.backend.exceptions.FileNotUploaded;
import java.io.File;
import java.io.IOException;
import lombok.SneakyThrows;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.xfer.FileSystemFile;
import net.schmizz.sshj.xfer.LocalSourceFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class SftpClient {

    private final static String USER_FILE_DESTINATION = "/home/savcaangela/ftp/userdata/%s";
    private final static String USER_CV_DESTINATION = "/home/savcaangela/ftp/cvs";
    private final Logger log = LoggerFactory.getLogger(SftpClient.class);

    @Value ("${java.io.tmpdir}")
    private String tmpDir;

    @Autowired
    private SFTPClient sftpClient;

    @Autowired
    private TerminalClient terminalClient;

    public String uploadUserFile(FileDto fileToUpload, String pathToLocalFile) {
        String userFolder = getUserFolder(fileToUpload);
        terminalClient.makeDirectory(userFolder);
        return uploadFile(fileToUpload.getFileName(), pathToLocalFile, format(USER_FILE_DESTINATION, userFolder));
    }

    public Resource getUserFile(FileDto fileToGet) {
        return new FileSystemResource(getFile(fileToGet));
    }

    public LocalSourceFile getCv() {
        // TODO implement after design
        return null;
    }

    public String uploadCv(String pathToFile, String fileName) {
        return uploadFile(fileName, pathToFile, USER_CV_DESTINATION);
    }

    @SneakyThrows
    private String uploadFile(String fileName, String fileLocation, String destination) {
        try {
            log.info(format("Transferring file %s to location %s", fileName, destination));
            sftpClient.put(new FileSystemFile(fileLocation), destination);
            return destination;
        } catch (IOException e) {
            log.error(format("Could not upload the file %s to destination %s", fileName, destination));
            throw new FileNotUploaded("Can't upload requested file, check logs for more details");
        } finally {
            File file = new File(fileLocation);
            if (!file.delete()) {
                log.error(format("File [%s] was not removed", fileLocation));
            }
        }
    }

    private File getFile(FileDto fileToGet) {
        String source = getUserFolder(fileToGet) + "/" + fileToGet.getFileName();
        String destination = tmpDir + fileToGet.getFileName();
        log.info(format("Getting file %s from the source %s", fileToGet.getFileName(), source));
        log.info(format("Will store the file into the location %s", destination));
        File file = new File(destination);
        log.info("Setting read and write permission for the file " + destination);
        if (!file.setReadable(true)) {
            log.warn("Could not set read permission for the file " + destination);
        }
        if (!file.setWritable(true)) {
            log.warn("Could not set write permission for the file " + destination);
        }
        try {
            sftpClient.get(source, destination);
        } catch (IOException exception) {
            log.error(format(
                "Could not file from the destination %s because of an unexpected error:", source, exception.getCause()));
        }
        return file;
    }

    private String getUserFolder(FileDto file) {
        String userFolder = file.getUser().getEmail().replaceAll("@.*$", "").trim();
        return format(USER_FILE_DESTINATION, userFolder);
    }
}
