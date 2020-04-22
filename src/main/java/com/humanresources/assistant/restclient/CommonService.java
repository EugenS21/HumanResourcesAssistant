package com.humanresources.assistant.restclient;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public abstract class CommonService<T> {

    @Autowired
    public WebClient restClient;

    public abstract String getURI();

    public abstract Class<T> getResponseClass();

    public List<T> getObjects() {
        return restClient.get()
            .uri(getURI())
            .retrieve()
            .bodyToFlux(getResponseClass())
            .collectList()
            .block();
    }

    public T postObject() {
        return null;
    }

    public T putObject() {
        return null;
    }

    public void deleteObjects() {

    }

}
