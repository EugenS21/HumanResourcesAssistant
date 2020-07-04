package com.humanresources.assistant.restclient.service;


import static com.humanresources.assistant.restclient.service.BaseUrl.BASE_URL;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;

@NoArgsConstructor (access = AccessLevel.PRIVATE)
public final class CustomWebClient {

    public static WebClient webClient = WebClient.builder()
        .baseUrl(BASE_URL)
        .build();

}
