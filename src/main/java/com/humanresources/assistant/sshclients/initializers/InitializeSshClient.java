package com.humanresources.assistant.sshclients.initializers;

import java.io.File;
import lombok.SneakyThrows;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import net.schmizz.sshj.userauth.keyprovider.KeyProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
final class InitializeSshClient {

    private static final String USERNAME = "savcaangela";
    private static final String PATH_TO_KEY = "src\\main\\resources\\sshkey\\ftpUserftpMachineLogin.ppk";
    private static final String PASSPHRASE = "keytoftp";

    @Value ("${app.ftp.host}")
    private String remoteHost;

    @Bean
    @SneakyThrows
    public SSHClient initializeClient() {
        SSHClient sshClient = new SSHClient();
        File keyFile = new File(PATH_TO_KEY);
        KeyProvider keyProvider = sshClient.loadKeys(keyFile.getPath(), PASSPHRASE);
        sshClient.addHostKeyVerifier(new PromiscuousVerifier());
        sshClient.connect(remoteHost, 22);
        sshClient.authPublickey(USERNAME, keyProvider);
        return sshClient;
    }
}
