package com.humanresources.assistant.sshclients.clients;

import lombok.SneakyThrows;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.connection.channel.direct.Session;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TerminalClient {

    @Autowired
    private SSHClient sshClient;

    @Autowired
    private Logger log;

    @SneakyThrows
    public void makeDirectory(String dirName) {
        Session session = sshClient.startSession();
        log.info("Creating directory " + dirName);
        session.exec("mkdir -p " + dirName);
        session.close();
    }
}
