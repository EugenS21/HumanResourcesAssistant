package com.humanresources.assistant.backend.authentication;

public interface AccessData {

    boolean signIn(String username, String password);

    boolean isUserSignedIn();

    boolean isUserInRole(String role);

    String getPrincipalName();
}
