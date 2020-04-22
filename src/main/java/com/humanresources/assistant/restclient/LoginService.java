package com.humanresources.assistant.restclient;

import com.humanresources.assistant.backend.payload.request.LoginRequest;
import com.humanresources.assistant.backend.tools.other.BeanUpdate;
import com.humanresources.assistant.restclient.model.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class LoginService {

    public static final String SIGN_IN = "signin";

    @Autowired
    private WebClient webClient;

    @Autowired
    private BeanUpdate<WebClient> beanUpdater;

    @Value ("${app.baseUrl}")
    private String baseUrl;

    public void login(LoginRequest loginRequest) {
        final Login token = webClient.post()
            .uri(SIGN_IN)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(loginRequest))
            .retrieve()
            .bodyToMono(Login.class)
            .block();

        final WebClient newWebClient = WebClient.builder()
            .baseUrl(baseUrl)
            .defaultHeader("Authorization", "Bearer " + token.getToken())
            .build();

        beanUpdater.updateBeanWithSpecificValue("initialize", newWebClient);
    }

}
