package com.humanresources.assistant.backend.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Log {

    @Bean
    public Logger initializeLog() {
        return LoggerFactory.getLogger(Log.class);
    }
}
