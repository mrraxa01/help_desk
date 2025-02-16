package com.marciorodrigues.auth_service_api.controllers;

import com.marciorodrigues.auth_service_api.models.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import models.exceptions.StandardError;
import models.requests.AuthenticateRequest;
import models.responses.AuthenticationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public interface AuthController {

    @Operation(summary = "Authenticate user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "User authenticated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticationResponse.class))),
            @ApiResponse(
                    responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(
                    responseCode = "401", description = "Unauthorized - Bad Credentials",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(
                    responseCode = "404", description = "User Not Fault",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))),

            @ApiResponse(
                    responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class)))
    })
    @PostMapping("/login")
    ResponseEntity<AuthenticationResponse> authenticate(@Valid @RequestBody final AuthenticateRequest request) throws Exception;

}
