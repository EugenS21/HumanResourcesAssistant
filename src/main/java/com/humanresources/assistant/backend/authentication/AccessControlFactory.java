package com.humanresources.assistant.backend.authentication;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AccessControlFactory {
    @Getter
    public static final AccessControlFactory SINGLETONE = new AccessControlFactory();

    public AccessData createAccessData(){
        return new AdminLogin();
    }
}
