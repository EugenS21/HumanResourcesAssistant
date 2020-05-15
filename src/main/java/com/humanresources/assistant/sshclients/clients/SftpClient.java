package com.humanresources.assistant.sshclients.clients;

import static java.lang.String.format;

import com.humanresources.assistant.backend.dto.FileDto;
import com.humanresources.assistant.backend.exceptions.FileNotUploaded;
import java.io.File;
import java.io.IOException;
import lombok.SneakyThrows;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.xfer.FileSystemFile;
import net.schmizz.sshj.xfer.LocalDestFile;
import net.schmizz.sshj.xfer.LocalSourceFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SftpClient {

    private final static String USER_FILE_DESTINATION = "/home/savcaangela/ftp/userdata/%s";
    private final static String USER_CV_DESTINATION = "/home/savcaangela/ftp/cvs";
    private final Logger log = LoggerFactory.getLogger(SftpClient.class);

    @Autowired
    private SFTPClient sftpClient;

    @Autowired
    private TerminalClient terminalClient;

    public String uploadUserFile(FileDto fileToUpload, String pathToLocalFile) {
        String username = fileToUpload.getUser().getEmail().replaceAll("@.*$", "").trim();
        terminalClient.makeDirectory(format(USER_FILE_DESTINATION, username));
        return uploadFile(fileToUpload.getFileName(), pathToLocalFile, format(USER_FILE_DESTINATION, username));
    }

    public LocalSourceFile getUserFile(String fileName) {
        // TODO implement after design
        return null;
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
            log.info(String.format("Transferring file %s to location %s", fileName, destination));
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

    private LocalDestFile getFile(String fileName) {
        log.info(String.format("Getting file %s from the destination %s", fileName));
//        TODO implement after designing
//        sftpClient.get("", "");
        return null;
    }
}
