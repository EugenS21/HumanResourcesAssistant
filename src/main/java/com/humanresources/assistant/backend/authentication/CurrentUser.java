package com.humanresources.assistant.backend.authentication;

import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinService;
import javax.servlet.http.Cookie;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CurrentUser {

    public static final String CURRENT_USER_SESSION_ATTRIBUTE_KEY = CurrentUser.class.getCanonicalName();

    public static String get(){
        String currentUser = (String) getCurrentRequest().getWrappedSession()
                .getAttribute(CURRENT_USER_SESSION_ATTRIBUTE_KEY);
        if (currentUser == null) {
            return "";
        } else {
            return currentUser;
        }
    }

    public static void set(String currentUser) {
        if (currentUser == null) {
            getCurrentRequest().getWrappedSession().removeAttribute(
                CURRENT_USER_SESSION_ATTRIBUTE_KEY);
        } else {
            getCurrentRequest().getWrappedSession().setAttribute(
                CURRENT_USER_SESSION_ATTRIBUTE_KEY, currentUser);
        }
    }

    public static void setBearer(String token, int maxAge) {
        Cookie authorization = new Cookie("Authorization", token);
        authorization.setMaxAge(maxAge);
        VaadinService.getCurrentResponse().addCookie(authorization);
    }

    private static VaadinRequest getCurrentRequest() {
        VaadinRequest request = VaadinService.getCurrentRequest();
        if (request == null) {
            throw new IllegalStateException(
                "No request bound to current thread.");
        }
        return request;
    }
}
