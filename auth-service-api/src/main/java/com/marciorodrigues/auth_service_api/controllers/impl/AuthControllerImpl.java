package com.marciorodrigues.auth_service_api.controllers.impl;

import com.marciorodrigues.auth_service_api.controllers.AuthController;
import com.marciorodrigues.auth_service_api.repository.UserRepository;
import com.marciorodrigues.auth_service_api.security.JWTAuthenticationImpl;
import com.marciorodrigues.auth_service_api.services.RefreshTokenService;
import com.marciorodrigues.auth_service_api.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import models.requests.AuthenticateRequest;
import models.requests.RefreshTokenRequest;
import models.responses.AuthenticationResponse;
import models.responses.RefreshTokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final JWTUtils jwtUtils;
    private final AuthenticationConfiguration  authenticationConfiguration;
    private final RefreshTokenService refreshTokenService;
    @Override
    public ResponseEntity<AuthenticationResponse> authenticate(final AuthenticateRequest request) throws Exception {
        log.info("Recebendo requisição de login para: {}", request.email());

        return ResponseEntity.ok().body(
                new JWTAuthenticationImpl(authenticationConfiguration.getAuthenticationManager(),jwtUtils)
                        .authenticate(request)
                        .withRefreshToken(refreshTokenService.save(request.email()).getId())
        );
    }

    @Override
    public ResponseEntity<RefreshTokenResponse> refreshToken(RefreshTokenRequest request) throws Exception {
        return ResponseEntity.ok().body(
                refreshTokenService.refreshToken(request.refreshToken()));
    }


}
