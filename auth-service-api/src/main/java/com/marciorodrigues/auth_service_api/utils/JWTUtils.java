package com.marciorodrigues.auth_service_api.utils;

import com.marciorodrigues.auth_service_api.security.dtos.UserDetailsDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtils {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;
    public String generateToken(final UserDetailsDTO userDetailsDTO) {
        return Jwts.builder()
                .claim("id", userDetailsDTO.getId())
                .claim( "name", userDetailsDTO.getName())
                .claim("authorities", userDetailsDTO.getAuthorities())
                .setSubject(userDetailsDTO.getUsername())
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .setExpiration(new Date(System.currentTimeMillis()+ expiration))
                .compact();
    }
}