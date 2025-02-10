package com.marciorodrigues.auth_service_api.controllers.impl;

import com.marciorodrigues.auth_service_api.controllers.AuthController;
import com.marciorodrigues.auth_service_api.security.JWTAuthenticationImpl;
import com.marciorodrigues.auth_service_api.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import models.requests.AuthenticateRequest;
import models.responses.AuthenticationResponse;
import org.springframework.http.ResponseEntity;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final JWTUtils jwtUtils;
    private final AuthenticationConfiguration  authenticationConfiguration;
    @Override
    public ResponseEntity<AuthenticationResponse> authenticate(AuthenticateRequest request) throws Exception {
        return ResponseEntity.ok().body(
                new JWTAuthenticationImpl(authenticationConfiguration.getAuthenticationManager(),jwtUtils).authenticate(request) );
    }
}
