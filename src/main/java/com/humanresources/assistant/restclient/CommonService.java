package com.humanresources.assistant.restclient;

import static com.humanresources.assistant.restclient.service.CustomWebClient.webClient;
import static org.springframework.web.reactive.function.BodyInserters.fromMultipartData;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.client.ExchangeStrategies.builder;

import com.vaadin.flow.router.NotFoundException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public abstract class CommonService<T> {

    private final Logger logger = LoggerFactory.getLogger(CommonService.class);

//    @Autowired
//    public WebClient webClient

    public abstract String getURI();

    public String getById() {return null;}

    public abstract Class<T> getResponseClass();

    public List<T> getObjects() {
        return webClient.get()
            .uri(getURI())
            .retrieve()
            .bodyToFlux(getResponseClass())
            .collectList()
            .block();
    }

    public List<T> getObjectsById() {
        return webClient.get()
            .uri(getById())
            .retrieve()
            .bodyToFlux(getResponseClass())
            .collectList()
            .block();
    }

    public T postObject(T body) {
        return webClient.post()
            .uri(getURI())
            .body(fromValue(body))
            .retrieve()
            .bodyToMono(getResponseClass())
            .block();
    }

    public <U extends Number> T putObject(U id, T body) {
        return webClient.put()
            .uri(getURI() + "/{id}", id)
            .body(fromValue(body))
            .retrieve()
            .bodyToMono(getResponseClass())
            .block();
    }

    public <U extends MultipartFile> T postObjectWithFile(T body, U file) {
        var bodyBuilder = new MultipartBodyBuilder();
        Resource fileToUpload = file.getResource();
        bodyBuilder.part("file", fileToUpload);
        Map<String, String> bodyByReflection = getBodyByReflection(body);
        for (String key : getBodyByReflection(body).keySet()) {
            bodyBuilder.part(key, bodyByReflection.get(key));
        }
        return webClient.post()
            .uri(getURI())
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .body(fromMultipartData(bodyBuilder.build()))
            .retrieve()
            .bodyToMono(getResponseClass())
            .block();
    }

    public <U extends Number> List<byte[]> getObjectById(U id) {
        return webClient
            .mutate()
            .exchangeStrategies(builder().codecs(codec -> codec.defaultCodecs().maxInMemorySize(52428800)).build())
            .build()
            .get()
            .uri(getURI() + "/{id}", id)
            .retrieve()
            .bodyToFlux(byte[].class)
            .collectList()
            .block();
    }

    public void deleteObject(String id) {
        webClient.delete()
            .uri(getURI() + "/{id}", id)
            .exchange()
            .block();
    }

    private Map<String, String> getBodyByReflection(T body) {
        return Stream.of(body.getClass().getDeclaredFields())
            .map(Field::getName)
            .filter(fieldName -> !(fieldName.toLowerCase().equals("id") || fieldName.toLowerCase().equals("user")))
            .flatMap(fieldName -> Stream.of(body.getClass().getDeclaredMethods())
                .filter(method -> method.getName().toLowerCase().contains(fieldName.toLowerCase())
                    && method.getName().toLowerCase().contains("get"))
                .map(method -> {
                    try {
                        return Map.of(fieldName, method.invoke(body).toString());
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new NotFoundException("Could not get value for " + fieldName);
                    }
                }))
            .collect(HashMap::new, HashMap::putAll, HashMap::putAll);
    }
}
