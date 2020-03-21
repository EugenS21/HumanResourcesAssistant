package com.humanresources.assistant.backend.authentication;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinSession;

public class AdminLogin implements AccessData {
    @Override
    public boolean signIn(String username, String password) {
        if (username == null || username.isEmpty()) {
            return false;
        }

        if (!password.equals("aYJag5%C*xV1Yjk6v&7Hhq8sPi07XnJqy4bRasFTxHV4Eyammb#")) {
            return false;
        }

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

    @Override
    public void signOut() {
        VaadinSession.getCurrent().getSession().invalidate();
        UI.getCurrent().navigate("");
    }
}
