package com.humanresources.assistant.sshclients.initializers;

import lombok.SneakyThrows;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.SFTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
final class InitializeSftpClient {

    @Autowired
    private SSHClient sshClient;

    @Bean
    @SneakyThrows
    public SFTPClient getSftpClient() {
        return sshClient.newSFTPClient();
    }
}
