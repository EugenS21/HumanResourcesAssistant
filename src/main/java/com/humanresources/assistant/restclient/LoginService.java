package com.humanresources.assistant.restclient;

import static com.humanresources.assistant.restclient.service.BaseUrl.BASE_URL;
import static com.humanresources.assistant.restclient.service.CustomWebClient.webClient;

import com.humanresources.assistant.backend.payload.request.LoginRequest;
import com.humanresources.assistant.restclient.model.Login;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class LoginService {

    public static final String SIGN_IN = "signin";

    private final Logger log = LoggerFactory.getLogger(LoginService.class);

    public String login(LoginRequest loginRequest) {
        final Login token = webClient.post()
            .uri(SIGN_IN)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(loginRequest))
            .retrieve()
            .bodyToMono(Login.class)
            .block();

        webClient = WebClient.builder()
            .baseUrl(BASE_URL)
            .defaultHeader("Authorization", "Bearer " + token.getToken())
            .build();

        return token.getToken();
    }

}
