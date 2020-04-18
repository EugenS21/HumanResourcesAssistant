package com.humanresources.assistant.backend.authentication;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class AccessControlFactory {

    @Autowired
    public UserLogin userLogin;
}
