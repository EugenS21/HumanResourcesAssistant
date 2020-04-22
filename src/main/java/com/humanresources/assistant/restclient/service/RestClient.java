package com.humanresources.assistant.restclient.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class RestClient {

    @Value ("${app.baseUrl}")
    private String baseUrl;

    @Bean ("initialize")
    public WebClient initialize() {
        return WebClient
            .builder()
            .baseUrl(baseUrl)
            .build();
    }
}
