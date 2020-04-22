package com.humanresources.assistant.backend.authentication;

import com.humanresources.assistant.backend.payload.request.LoginRequest;
import com.humanresources.assistant.restclient.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class UserLogin implements AccessData {

    @Autowired
    WebClient webClient;

    @Autowired
    LoginService loginService;

    @Override
    public boolean signIn(String username, String password) {
        final LoginRequest loginRequest = LoginRequest.builder()
            .username(username)
            .password(password)
            .build();

        loginService.login(loginRequest);
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
