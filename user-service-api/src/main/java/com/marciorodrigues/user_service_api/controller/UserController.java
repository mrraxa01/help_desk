package com.marciorodrigues.user_service_api.controller;

import com.marciorodrigues.user_service_api.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import models.exceptions.StandardError;
import models.responses.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "UsersController", description = "Controller responsible for users operations")
@RequestMapping("/api/users")
public interface UserController {


    @Operation(summary = "Find user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Found!"),
            @ApiResponse(responseCode = "404", description = "User Not Found!", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StandardError.class)
            )),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StandardError.class)
            ))}
    )
    @GetMapping("/{id}")
    ResponseEntity<UserResponse> findById(
            @Parameter(description = "UserID", required = true, example = "674e8827663399785fabc1d3")
            @PathVariable(name = "id") final String id);



}
