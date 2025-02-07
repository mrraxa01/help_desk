package com.marciorodrigues.auth_service_api.controllers.impl;

import com.marciorodrigues.auth_service_api.controllers.AuthController;
import models.requests.AuthenticateRequest;
import models.responses.AuthenticationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthControllerImpl implements AuthController {
    @Override
    public ResponseEntity<AuthenticationResponse> authenticate(AuthenticateRequest request) {
        return ResponseEntity.ok().body(new AuthenticationResponse(
                "Bearer ",
                "token"));
    }
}
