package com.humanresources.assistant.sshclients.clients;

import com.humanresources.assistant.backend.exceptions.FileNotUploaded;
import com.humanresources.assistant.sshclients.model.EmployeeFile;
import java.io.IOException;
import lombok.SneakyThrows;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.xfer.LocalSourceFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SftpClient {

    private final static String USER_FILE_DESTINATION = "/home/savcaangela/ftp/userdata/%s";
    private final static String USER_CV_DESTINATION = "/home/savcaangela/ftp/cvs/%s";
    private final Logger log = LoggerFactory.getLogger(SftpClient.class);
    @Autowired
    private SFTPClient sftpClient;

    @Autowired
    private TerminalClient terminalClient;

    public String uploadUserFile(EmployeeFile fileToUpload) {
        terminalClient.makeDirectory(fileToUpload.getEmployeeDto().getFirstName());
        return uploadFile(fileToUpload, USER_FILE_DESTINATION);
    }

    public String uploadCv(LocalSourceFile fileToUpload) {
        return uploadFile(fileToUpload, String.format(USER_CV_DESTINATION, fileToUpload.getName()));
    }

    @SneakyThrows
    private String uploadFile(LocalSourceFile fileToUpload, String destination) {
        try {
            sftpClient.put(fileToUpload, destination);
            return destination;
        } catch (IOException e) {
            log.error(String.format("Could not upload the file %s to destination %s", fileToUpload.getName(), destination));
            throw new FileNotUploaded("Can't upload requested file, check logs for more details");
        }
    }
}
