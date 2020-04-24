package com.humanresources.assistant.restclient;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

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

    public T postObject() {
        return null;
    }

    public T putObject() {
        return null;
    }

    public void deleteObject(String id) {
        logger.info(
            requireNonNull(
                restClient.delete()
                    .uri(format("%s?id=%s", getURI(), id))
                    .exchange()
                    .block())
                .statusCode()
                .getReasonPhrase());
    }

}
