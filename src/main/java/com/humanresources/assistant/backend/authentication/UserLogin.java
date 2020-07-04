package com.humanresources.assistant.backend.authentication;

import com.humanresources.assistant.backend.payload.request.LoginRequest;
import com.humanresources.assistant.restclient.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserLogin implements AccessData {

    @Autowired
    LoginService loginService;

    @Value ("${app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Override
    public boolean signIn(String username, String password) {
        final LoginRequest loginRequest = LoginRequest.builder()
            .username(username)
            .password(password)
            .build();

        String login = loginService.login(loginRequest);
        CurrentUser.set(username);
        CurrentUser.setBearer(login, jwtExpirationMs);
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
