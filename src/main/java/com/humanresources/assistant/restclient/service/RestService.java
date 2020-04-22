package com.humanresources.assistant.restclient.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestService {

//    private HttpHeaders httpHeaders = new HttpHeaders();

//    @Bean
//    private HttpEntity<?> setHttpHeaders(HttpHeaders httpHeaders){
//        this.httpHeaders = httpHeaders;
//        httpHeaders.setContentType(APPLICATION_JSON);
//        return new HttpEntity<>(httpHeaders);
//    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(new ObjectMapper());
        restTemplate.getMessageConverters().add(converter);
        return restTemplate;
    }
}
