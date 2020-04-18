package com.humanresources.assistant.backend.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {

    private String token;

    public JwtResponse(String accessToken) {
        this.token = accessToken;
    }
}
