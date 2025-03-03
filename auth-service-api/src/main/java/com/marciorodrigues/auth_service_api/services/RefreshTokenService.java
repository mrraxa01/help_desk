package com.marciorodrigues.auth_service_api.services;

import com.marciorodrigues.auth_service_api.models.RefreshToken;
import com.marciorodrigues.auth_service_api.repository.RefreshTokenRepository;
import com.marciorodrigues.auth_service_api.security.dtos.UserDetailsDTO;
import com.marciorodrigues.auth_service_api.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import models.exceptions.RefreshTokenExpired;
import models.exceptions.ResourceNotFoundExceptions;
import models.responses.RefreshTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    @Value("${jwt.expiration-sec.refresh-token}")
    private Long refreshTokenExpirationSec;

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserDetailsService userDetailsService;
    private final JWTUtils jwtUtils;
    public RefreshToken save(final String username){
        return  refreshTokenRepository.save(RefreshToken.builder()
                .id(UUID.randomUUID().toString())
                .createdAt(now())
                .username(username)
                .expiresAt(now().plusSeconds(refreshTokenExpirationSec))
                .build());
    }

    public RefreshTokenResponse refreshToken(final String refreshTokenId){
    final var refreshToken = refreshTokenRepository.findById(refreshTokenId)
            .orElseThrow(() -> new ResourceNotFoundExceptions("Refresh token not found.Id: " + refreshTokenId));
    if (refreshToken.getExpiresAt().isBefore(now()))
        throw new RefreshTokenExpired("Refresh token expired.Id: " + refreshTokenId);

    return new RefreshTokenResponse(
            jwtUtils.generateToken((UserDetailsDTO) userDetailsService.loadUserByUsername(refreshToken.getUsername()))
    );
    }
}