package com.humanresources.assistant.backend.authentication;

import static java.util.Objects.requireNonNull;

import com.humanresources.assistant.backend.payload.request.LoginRequest;
import com.vaadin.flow.server.VaadinService;
import java.util.LinkedHashMap;
import javax.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserLogin implements AccessData {

    @Autowired
    private RestTemplate restTemplate;

    @Value ("${app.baseUrl}")
    private String baseUrl;

    @Value ("${app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Override
    public boolean signIn(String username, String password) {
        final LinkedHashMap responses;
        final String url = baseUrl + "signin";
        final LoginRequest loginRequest = LoginRequest.builder()
            .username(username)
            .password(password)
            .build();

        try {
            responses = (LinkedHashMap) restTemplate.postForObject(url, loginRequest, Object.class);
        } catch (Exception e) {
            return false;
        }
        final String token = (String) requireNonNull(responses).get("token");
        Cookie authenticationCookie = new Cookie("Authentication", token);
        authenticationCookie.setMaxAge(jwtExpirationMs / 1000);
        VaadinService.getCurrentResponse().addCookie(authenticationCookie);
        CurrentUser.set(username);
        return true;
    }

    @Override
    public boolean isUserSignedIn() {
        return !CurrentUser.get().isEmpty();
    }

    @Override
    public boolean isUserInRole(String role) {
        if ("admin".equals(role)) {
            // Only the "admin" user is in the "admin" role
            return getPrincipalName().equals("admin");
        }

        // All users are in all non-admin roles
        return true;
    }

    @Override
    public String getPrincipalName() {
        return CurrentUser.get();
    }
}
