package com.humanresources.assistant.backend.payload.response;

import com.humanresources.assistant.backend.security.services.UserDetailsImpl;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
public class JwtResponse {

    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private List<String> roles;

    public JwtResponse(String accessToken, UserDetailsImpl userData) {
        this.token = accessToken;
        this.id = userData.getId();
        this.username = userData.getUsername();
        this.email = userData.getEmail();
        this.roles = userData.getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());
    }
}
