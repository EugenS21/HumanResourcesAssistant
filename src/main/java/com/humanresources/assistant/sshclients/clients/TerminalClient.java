package com.humanresources.assistant.sshclients.clients;

import lombok.SneakyThrows;
import net.schmizz.sshj.connection.channel.direct.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TerminalClient {

    @Autowired
    private Session session;

    @SneakyThrows
    public void makeDirectory(String dirName) {
        session.exec("mkdir -p " + dirName);
    }
}
