package com.humanresources.assistant.backend.security.jwt;

import static io.jsonwebtoken.Jwts.parser;

import com.humanresources.assistant.backend.dto.UserDto;
import com.humanresources.assistant.backend.security.services.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {

    public static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value ("${app.jwtSecret}")
    private String jwtSecret;

    @Value ("${app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
            .claim("id", userPrincipal.getId())
            .claim("username", userPrincipal.getUsername())
            .claim("email", userPrincipal.getEmail())
            .claim("roles", userPrincipal.getAuthorities())
            .setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();
    }

    public String getUserNameFromJwtToken(String authToken) {
        return (String) parser().setSigningKey(jwtSecret).parseClaimsJws(authToken).getBody().get("username");
    }

    public UserDto getUserDetailsFromJwt(String authToken) {
        Claims jwtClaims = parser().setSigningKey(jwtSecret)
            .parseClaimsJws(authToken.replaceAll("Bearer ", ""))
            .getBody();
        return UserDto.builder()
            .username((String) jwtClaims.get("username"))
            .id(Long.valueOf((Integer) jwtClaims.get("id")))
            .email((String) jwtClaims.get("email"))
            .role((String) ((LinkedHashMap) ((ArrayList) jwtClaims.get("roles")).get(0)).get("authority"))
            .build();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is not supported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}