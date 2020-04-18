package com.humanresources.assistant.backend.tools.token;

import com.humanresources.assistant.backend.enums.ERole;
import com.humanresources.assistant.backend.model.UserDetails;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.impl.DefaultJwtParser;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenDecoder {

    private final String jwtSecret;

    @Autowired
    public JwtTokenDecoder(@Value ("${app.jwtSecret}") String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    public UserDetails parseJwt(String jwt) {
        JwtParser jwtParser = new DefaultJwtParser();
        jwtParser.setSigningKey(jwtSecret);
        final DefaultClaims requestClaims = (DefaultClaims) jwtParser.parse(jwt).getBody();
        final List<String> roles = (List<String>) ((ArrayList) requestClaims.get("roles")).stream()
            .flatMap(el -> ((LinkedHashMap) el).values().stream())
            .collect(Collectors.toList());

        final List<ERole> rolesList = roles.stream()
            .map(String::toUpperCase)
            .map(ERole::valueOf)
            .collect(Collectors.toList());

        return UserDetails.builder()
            .id((Integer) requestClaims.get("id"))
            .username((String) requestClaims.get("username"))
            .email((String) requestClaims.get("email"))
            .roles(rolesList)
            .build();
    }
}
