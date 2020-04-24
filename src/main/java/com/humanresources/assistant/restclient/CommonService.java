package com.humanresources.assistant.restclient;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public abstract class CommonService<T> {

    private final Logger logger = LoggerFactory.getLogger(CommonService.class);

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

    public T postObject(T body) {
        return restClient.post()
            .uri(getURI())
            .body(fromValue(body))
            .retrieve()
            .bodyToMono(getResponseClass())
            .block();
    }

    public <U extends Number> T putObject(U id, T body) {
        return restClient.put()
            .uri(getURI() + "/{id}", id)
            .body(fromValue(body))
            .retrieve()
            .bodyToMono(getResponseClass())
            .block();
    }

    public void deleteObject(String id) {
        restClient.delete()
            .uri(getURI() + "/{id}", id)
            .exchange()
            .block();
    }

}
