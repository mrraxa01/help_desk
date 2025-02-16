package com.marciorodrigues.auth_service_api.security;

import com.marciorodrigues.auth_service_api.security.dtos.UserDetailsDTO;
import com.marciorodrigues.auth_service_api.utils.JWTUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import models.requests.AuthenticateRequest;
import models.responses.AuthenticationResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class JWTAuthenticationImpl {

    private final AuthenticationManager authenticationManager;
    private final JWTUtils jwtUtils;

    public AuthenticationResponse authenticate(final AuthenticateRequest request) {
        try {
            log.info("Authenticating user {}", request.email());
            log.info("authenticationManager {}", authenticationManager);
            final var authResult = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(),request.password()));
            log.info("authResult {}", authResult.getPrincipal());
            return buildAuthenticationResponse((UserDetailsDTO) authResult.getPrincipal());
        }catch (BadCredentialsException exception) {
            log.error("Error authenticating user : {}", exception.getMessage());
            throw new BadCredentialsException("Bad Credentials - username or password invalid!", exception);
        }

    }

//    public AuthenticationResponse authenticate(final AuthenticateRequest request, final AuthenticationManager authenticationManager) {
//        try {
//            log.info("Iniciando autenticação para {}", request.email());
//
//            final var authResult = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
//            );
//
//            log.info("Autenticação bem-sucedida para {}", request.email());
//
//            return buildAuthenticationResponse((UserDetailsDTO) authResult.getPrincipal());
//        } catch (Exception e) {
//            log.error("Erro durante autenticação: {}", e.getMessage(), e);
//            throw new BadCredentialsException("Email ou senha inválidos", e);
//        }
//
//
//
//    }

    protected AuthenticationResponse buildAuthenticationResponse(final UserDetailsDTO userDetailsDTO) {
        log.info("Successfully authenticated user {} ", userDetailsDTO.getUsername());
        final var token = jwtUtils.generateToken(userDetailsDTO);

        return AuthenticationResponse.builder()
                .type("JWT")
                .token("Bearer "+ token)
                .build();
    }

}
