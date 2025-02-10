package com.marciorodrigues.auth_service_api.security;

import com.marciorodrigues.auth_service_api.security.dtos.UserDetailsDTO;
import com.marciorodrigues.auth_service_api.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import models.requests.AuthenticateRequest;
import models.responses.AuthenticationResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Log4j2
@RequiredArgsConstructor
public class JWTAuthenticationImpl {

    private final AuthenticationManager authenticationManager;
    private final JWTUtils jwtUtils;
    public AuthenticationResponse authenticate(final AuthenticateRequest request) {
        try {
            log.info("Authenticating user {}", request.email());
            final var authResult = authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(request.email(),request.password()));

            return buildAuthenticationResponse((UserDetailsDTO) authResult.getPrincipal());
        }catch (BadCredentialsException exception) {
            log.error("Error authenticating user : {}", exception.getMessage());
            throw new BadCredentialsException("Email or password invalid");
        }

    }

    protected AuthenticationResponse buildAuthenticationResponse(final UserDetailsDTO userDetailsDTO) {
        log.info("Successfully authenticated user {} ", userDetailsDTO.getUsername());
        final var token = jwtUtils.generateToken(userDetailsDTO);

        return AuthenticationResponse.builder()
                .type("JWT")
                .token("Bearer "+ token)
                .build();
    }

}
