package com.humanresources.assistant.sshclients.initializers;

import lombok.SneakyThrows;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.connection.channel.direct.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
final class InitializeSession {

    @Autowired
    private SSHClient sshClient;

    @Bean
    @SneakyThrows
    public Session getSshSession() {
        return sshClient.startSession();
    }
}
